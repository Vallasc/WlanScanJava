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
    private File executable = null;

    private final String windowsBinPath = ".\\bin\\WlanScan.exe";
    private final String linuxBinPath = "./bin/WlanScan";

    private final Gson gson = new Gson();

    public WlanScanner(){
        if(SystemUtils.IS_OS_WINDOWS && ArchUtils.getProcessor().is64Bit() && ArchUtils.getProcessor().isX86()){
            isWindows64 = true;
            try {
                executable = extractFromResources("/WlanScan.exe", windowsBinPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(SystemUtils.IS_OS_LINUX && ArchUtils.getProcessor().is64Bit() && ArchUtils.getProcessor().isX86()){
            isLinux64 = true;
        }
    }


    public APInfo[] scanNetworks() throws OperatingSystemNotDefinedException, IOException {
        if(isWindows64)
            return scanWindows();
        else if(isLinux64)
            return new APInfo[0];
        else 
            throw new OperatingSystemNotDefinedException();
    }

    private APInfo[] scanWindows() throws IOException{
        Process process = Runtime.getRuntime().exec(windowsBinPath + " -t -n -j");
        String outJson = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_16LE);
        APInfo[] out = gson.fromJson(outJson, APInfo[].class);
        return out;
    }


    public File extractFromResources(String fileInputPath, String fileOutputPath) throws IOException{
        File file = new File(fileOutputPath);
        Files.createDirectories(file.getParentFile().toPath());
        file.createNewFile();
        InputStream link = WlanScanner.class.getResourceAsStream(fileInputPath);
        Files.copy(link, file.getAbsoluteFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
        link.close();
        return file;
    }

    public class OperatingSystemNotDefinedException extends Exception{}
}
