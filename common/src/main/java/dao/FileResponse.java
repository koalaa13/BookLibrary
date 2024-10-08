package dao;

public class FileResponse {
    private String UUID;
    private String type;
    private long size;

    public FileResponse() {
    }

    public FileResponse(String name, String type, long size) {
        this.UUID = name;
        this.type = type;
        this.size = size;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "FileResponse{" +
                "name='" + UUID + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                '}';
    }
}
