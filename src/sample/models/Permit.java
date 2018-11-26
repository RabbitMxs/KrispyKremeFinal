package sample.models;

public class Permit {

    int id_permit;
    String type;

    public Permit(int id_permit, String type) {
        this.id_permit = id_permit;
        this.type = type;
    }

    public int getId_permit() { return id_permit; }

    public void setId_permit(int id_permit) {this.id_permit = id_permit; }

    public String getType() { return type; }

    public void setType(String type) {this.type = type; }

    @Override
    public String toString() {
        return "Permit{" +
                "id_permit=" + id_permit +
                ", type='" + type + '\'' +
                '}';
    }
}
