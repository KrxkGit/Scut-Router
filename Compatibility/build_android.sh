# 下列脚本用于构建安卓版本共享库
#
# 保存原配置
go env > backup_GoEnv.txt

SDK_HOME=${HOME}/AppData/Local/Android/Sdk

go env -w CGO_ENABLED=1
go env -w GOARCH=arm64
go env -w GOOS=android

# 下列请替换此为NDK 的 clang 路径
PREFIX="${SDK_HOME}/ndk/27.0.12077973/toolchains/llvm/prebuilt/windows-x86_64/bin/"
go env -w CC="${PREFIX}/aarch64-linux-android34-clang"
go env -w CXX="${PREFIX}/aarch64-linux-android34-clang++"

go build -o ./out/libSSHCommand.so -buildmode=c-shared CompatibleMain.go CompatibleRunSSH.go 

# 拷贝 so 到JniLibs 用于一并打包
dest_folder="../Android/app/src/main/jniLibs/arm64-v8a/"
if [ -d "$folder" ]; then
    rm -rf ${dest_folder}
fi

mkdir -p ${dest_folder}
cp ./out/libSSHCommand.so ${dest_folder}/libSSHCommand.so