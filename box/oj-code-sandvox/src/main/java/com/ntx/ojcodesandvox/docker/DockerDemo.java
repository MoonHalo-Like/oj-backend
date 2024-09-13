package com.ntx.ojcodesandvox.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListImagesCmd;
import com.github.dockerjava.api.command.PingCmd;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DockerClientBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DockerDemo
 * @Author ntx
 * @Description docker demo
 */
@Slf4j
public class DockerDemo {
//    public static void main(String[] args) {
//        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
//        //拉取容器
//        String image = "openjdk:8-alpine";
//        ListImagesCmd listImagesCmd = dockerClient.listImagesCmd();
//        List<Image> images = listImagesCmd.exec();
//        List<String> hasImage = images.stream().map(image1 -> {
//                    String[] repoTags = image1.getRepoTags();
//                    for (String repoTag : repoTags) {
//                        return repoTag;
//                    }
//                    return null;
//                })
//                .filter(image::equals)
//                .collect(Collectors.toList());
////        System.out.println(hasImage);
//    }
}
