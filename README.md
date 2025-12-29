# build
```shell
./gradlew clean build -x test
```
# ~~ native build ~~ 
## ~~ windows 环境下必须使用vs提供的一个命令行工具，否则失败 ~~
```shell
./gradlew clean build nativeBuild -x test
```
# image
```shell
docker build -t tools .
```
# native build image
```shell
./gradlew bootBuildImage
```

# 根据每年假日办文件
## [2023年](./work-day/2023.md)
## [2024年](./work-day/2024.md)
## [2025年](./work-day/2025.md)
## [2026年](./work-day/2026.md)