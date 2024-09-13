package com.ntx.ojcodesandvox;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.ntx.ojcodesandvox.model.ExecuteCodeRequest;
import com.ntx.ojcodesandvox.model.ExecuteCodeResponse;
import com.ntx.ojcodesandvox.model.ExecuteMessage;
import com.ntx.ojcodesandvox.model.JudgeInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName CodeSandboxImpl
 * @Author ntx
 * @Description java模板方法docker实现
 */
@Component
public class JavaDockerCodeSandbox extends JavaCodeSandboxTemple {
    //超时时间
    private static final long TIME_OUT = 10000L;

    private static Boolean FIRST_INIT = true;

    private static String CONTAINERID;

    /**
     * 3、创建容器，将文件复制到容器内
     *
     * @param userCodeFile
     * @param inputList
     * @return
     */
    @Override
    public ArrayList<ExecuteMessage> runCode(File userCodeFile, List<String> inputList)   {
        String userCodeParentPath = userCodeFile.getParentFile().getAbsolutePath();
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
        //拉取容器
        String image = "openjdk:8-jdk-alpine";
        //查询镜像是否存在
        ListImagesCmd listImagesCmd = dockerClient.listImagesCmd();
        List<Image> images = listImagesCmd.exec();
        List<String> hasImage = images.stream().map(image1 -> {
                    String[] repoTags = image1.getRepoTags();
                    for (String repoTag : repoTags) {
                        return repoTag;
                    }
                    return null;
                })
                .filter(image::equals)
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(hasImage)) {
            FIRST_INIT = false;
            System.out.println("镜像存在:" + hasImage);
        }
        if (FIRST_INIT) {
            PullImageCmd pullImageCmd = dockerClient.pullImageCmd(image);
            PullImageResultCallback pullImageResultCallback = new PullImageResultCallback() {
                @Override
                public void onNext(PullResponseItem item) {
                    System.out.println("下载状态：" + item.getStatus());
                    super.onNext(item);
                }
            };
            try {
                pullImageCmd.exec(pullImageResultCallback).awaitCompletion();
            } catch (InterruptedException e) {
                System.out.println("拉取镜像异常");
                throw new RuntimeException(e);
            }
            System.out.println("下载完成");
        }


        //4、创建容器
        CreateContainerCmd containerCmd = dockerClient.createContainerCmd(image);

        HostConfig hostConfig = new HostConfig();
        //限制内存
        hostConfig.withMemory(100 * 1000 * 1000L);
        //内存交换
        hostConfig.withMemorySwap(0L);
        //限制cpu个数
        hostConfig.withCpuCount(1L);
        //容器文件映射（容器挂在目录）
        hostConfig.setBinds(new Bind(userCodeParentPath, new Volume("/app")));
        CreateContainerResponse createContainerResponse = containerCmd
                //警用网络
                .withNetworkDisabled(true)
                //禁止写入根目录
                .withReadonlyRootfs(true)
                .withHostConfig(hostConfig)
                .withAttachStdin(true)
                .withAttachStderr(true)
                .withAttachStdout(true)
                .withTty(true)
                .exec();
        System.out.println(createContainerResponse);
        String containerId = createContainerResponse.getId();
        CONTAINERID = containerId;

        //启动容器
        dockerClient.startContainerCmd(containerId).exec();

        //执行命令并获取结果
        ArrayList<ExecuteMessage> executeMessageList = new ArrayList<>();
        for (String inputArgs : inputList) {
            StopWatch stopWatch = new StopWatch();
            String[] inputArgsArray = inputArgs.split(" ");
            //docker exec angry_noyce java -cp /app Main 1 2
            String[] cmdArray = ArrayUtil.append(new String[]{"java", "-cp", "/app", "Main"}, inputArgsArray);
            ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
                    .withCmd(cmdArray)
                    .withAttachStderr(true)
                    .withAttachStdout(true)
                    .withAttachStdin(true)
                    .exec();
            System.out.println("创建执行命令：" + execCreateCmdResponse);

            ExecuteMessage executeMessage = new ExecuteMessage();
            final String[] message = {null};
            final String[] errorMessage = {null};
            long time = 0;
            final Boolean[] timeout = {true};
            String execId = execCreateCmdResponse.getId();
            if (StrUtil.isBlank(execId)) {
                return null;
            }
            ExecStartResultCallback execStartResultCallback = new ExecStartResultCallback() {

                @Override
                public void onComplete() {
                    timeout[0] = false;
                    super.onComplete();
                }

                @Override
                public void onNext(Frame frame) {
                    StreamType streamType = frame.getStreamType();
                    if (StreamType.STDERR.equals(streamType)) {
                        errorMessage[0] = new String(frame.getPayload()).replaceAll("\\r?\\n$", "");
                        System.out.println("输出错误结果：" + errorMessage[0] + "结束");
                    } else {
                        message[0] = new String(frame.getPayload()).replaceAll("\\r?\\n$", "");
                        System.out.println("输出结果：" + message[0] + "结束");
                    }
                    super.onNext(frame);
                }
            };

            //获取占用内存
            final Long[] maxMemory = {0L};
            StatsCmd statsCmd = dockerClient.statsCmd(containerId);
            ResultCallback<Statistics> statisticsResultCallback = statsCmd.exec(new ResultCallback<Statistics>() {
                @Override
                public void onNext(Statistics statistics) {
                    Long usage = statistics.getMemoryStats().getUsage();
                    System.out.println("内存占用：" + usage);
//                    maxMemory[0] = Math.max(maxMemory[0], usage);
                    if (maxMemory[0] < usage) {
                        maxMemory[0] = usage;
                    }
                    System.out.println("最大内存：" + maxMemory[0]);

                }

                @Override
                public void onStart(Closeable closeable) {

                }

                @Override
                public void onError(Throwable throwable) {

                }

                @Override
                public void onComplete() {

                }

                @Override
                public void close() throws IOException {

                }
            });
            statsCmd.exec(statisticsResultCallback);


            try {
                stopWatch.start();
                dockerClient.execStartCmd(execId)
                        .exec(execStartResultCallback)
//                        .awaitCompletion(TIME_OUT, TimeUnit.MICROSECONDS);
                        .awaitCompletion();
                stopWatch.stop();
                time = stopWatch.getLastTaskTimeMillis();
                statsCmd.close();
            } catch (InterruptedException e) {
                throw new RuntimeException("执行错误", e);
            }
            executeMessage.setMessage(message[0]);
            executeMessage.setErrorMessage(errorMessage[0]);
            executeMessage.setTime(time);
            Long maxMemory1 = maxMemory[0];
            System.out.println("--------------------" + maxMemory1);
            executeMessage.setMemory(maxMemory1);
            executeMessageList.add(executeMessage);
        }
        return executeMessageList;
    }

    @Override
    public boolean deleteFile(File userCodeFile) {
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
        //停止容器
        dockerClient.stopContainerCmd(CONTAINERID).exec();
        //删除容器
        dockerClient.removeContainerCmd(CONTAINERID).exec();
        return super.deleteFile(userCodeFile);
    }
}
