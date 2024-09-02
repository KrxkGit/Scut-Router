# 下列脚本用于构建安卓版本共享库
#
# 保存原配置
go env > backup_GoEnv.txt

SDK_HOME=${HOME}/AppData/Local/Android/Sdk

go env -w CGO_ENABLED=1
go env -w GOOS=android # 指定安卓系统

# 编译并拷贝 so 到JniLibs 用于一并打包
function build_and_copy_to_jnilibs {
	go build -o ./out/libSSHCommand.so -buildmode=c-shared CompatibleMain.go CompatibleRunSSH.go
	dest_folder="../Android/app/src/main/jniLibs/$1/"
	if [ -d "$folder" ]; then
		rm -rf ${dest_folder}
	fi
	
	mkdir -p ${dest_folder}
	cp ./out/libSSHCommand.h ${dest_folder}/libSSHCommand.h
	cp ./out/libSSHCommand.so ${dest_folder}/libSSHCommand.so
}


# 配置交叉编译环境：下列请替换此为NDK 的 clang 路径
PREFIX="${SDK_HOME}/ndk/27.0.12077973/toolchains/llvm/prebuilt/windows-x86_64/bin/"


# arm 64
go env -w GOARCH=arm64
go env -w CC="${PREFIX}/aarch64-linux-android34-clang"
go env -w CXX="${PREFIX}/aarch64-linux-android34-clang++"

build_and_copy_to_jnilibs "arm64-v8a"

# arm
go env -w GOARCH=arm
go env -w CC="${PREFIX}/armv7a-linux-androideabi34-clang"
go env -w CXX="${PREFIX}/armv7a-linux-androideabi34-clang++"

build_and_copy_to_jnilibs "armeabi-v7a"

# x86_64
go env -w GOARCH=amd64
go env -w CC="${PREFIX}/x86_64-linux-android34-clang"
go env -w CXX="${PREFIX}/x86_64-linux-android34-clang++"

build_and_copy_to_jnilibs "x86_64"

# x86 (暂时不支持)
#go env -w GOARCH=386
#go env -w CC="${PREFIX}/i686-linux-android34-clang"
#go env -w CXX="${PREFIX}/i686-linux-android34-clang++"
#
#build_and_copy_to_jnilibs "x86"