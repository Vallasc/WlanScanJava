package io.github.vallasc;

import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.google.gson.Gson;

import org.apache.commons.lang3.ArchUtils;

public class WlanScanner {
    private boolean isWindows64 = false;
    private boolean isLinux64 = false;

    private final String windowsBinPath = ".\\bin\\WlanScan.exe";
    private final String linuxBinPath = "./bin/WlanScanLinux";

    private final Gson gson = new Gson();

    public WlanScanner(){
        if(SystemUtils.IS_OS_WINDOWS && ArchUtils.getProcessor().is64Bit() && ArchUtils.getProcessor().isX86()){
            isWindows64 = true;
            try {
                extractFromResources("/win64/WlanScan.exe", windowsBinPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(SystemUtils.IS_OS_LINUX && ArchUtils.getProcessor().is64Bit() && ArchUtils.getProcessor().isX86()){
            isLinux64 = true;
            try {
                extractFromResources("/linux64/WlanScanLinux", linuxBinPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public APInfo[] scanNetworks() throws OperatingSystemNotDefinedException, IOException {
        if(isWindows64)
            return scanWindows64();
        else if(isLinux64)
            return scanLinux64();
        else 
            throw new OperatingSystemNotDefinedException();
    }

    private APInfo[] scanWindows64() throws IOException{
        Process process = Runtime.getRuntime().exec(windowsBinPath + " -t -n -j");
        String outJson = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_16LE);
        return gson.fromJson(outJson, APInfo[].class);
    }

    private APInfo[] scanLinux64() throws IOException{
        Runtime.getRuntime().exec("chmod +x " + linuxBinPath);
        Process process = Runtime.getRuntime().exec(linuxBinPath + " -j");
        String outJson = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        return gson.fromJson(outJson, APInfo[].class);
    }


    public File extractFromResources(String fileInputPath, String fileOutputPath) throws IOException{
        File file = new File(fileOutputPath);
        Files.createDirectories(file.getParentFile().toPath());
        file.createNewFile();
        InputStream link = WlanScanner.class.getResourceAsStream(fileInputPath);
        Files.copy(link, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        link.close();
        return file;
    }

    public class OperatingSystemNotDefinedException extends Exception{}
}
