package com.gb.ir.model;

public class LSConfig {
    private String lsName;
    private String rsIp;
    private int rsPort;
    private boolean isExecutable;
    private Boolean ifSave;
    private String savePath;
    private String fileName;
    private String fileSuffix;
    private String exeCommandWithMsg;
    private String exeCommandDirectly;
    private String exeCommandWithFile;

    public String getLsName() {
        return lsName;
    }

    public void setLsName(String lsName) {
        this.lsName = lsName;
    }

    public String getRsIp() {
        return rsIp;
    }

    public void setRsIp(String rsIp) {
        this.rsIp = rsIp;
    }

    public int getRsPort() {
        return rsPort;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public void setRsPort(int rsPort) {
        this.rsPort = rsPort;
    }

    public boolean isExecutable() {
        return isExecutable;
    }

    public void setExecutable(boolean executable) {
        isExecutable = executable;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getExeCommandWithMsg() {
        return exeCommandWithMsg;
    }

    public void setExeCommandWithMsg(String exeCommandWithMsg) {
        this.exeCommandWithMsg = exeCommandWithMsg;
    }

    public String getExeCommandDirectly() {
        return exeCommandDirectly;
    }

    public void setExeCommandDirectly(String exeCommandDirectly) {
        this.exeCommandDirectly = exeCommandDirectly;
    }

    public String getExeCommandWithFile() {
        return exeCommandWithFile;
    }

    public void setExeCommandWithFile(String exeCommandWithFile) {
        this.exeCommandWithFile = exeCommandWithFile;
    }

    public Boolean getIfSave() {
        return ifSave;
    }

    public void setIfSave(Boolean ifSave) {
        this.ifSave = ifSave;
    }
}
