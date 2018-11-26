package sample.models;

public class Package {
    int id_package;
    String name_pa,description_pa;

    public Package(int id_package, String name_pa, String description_pa) {
        this.id_package = id_package;
        this.name_pa = name_pa;
        this.description_pa = description_pa;
    }

    public int getId_package() {return id_package; }

    public void setId_package(int id_package) {this.id_package = id_package; }

    public String getName_pa() {return name_pa; }

    public void setName_pa(String name_pa) {this.name_pa = name_pa; }

    public String getDescription_pa() {return description_pa; }

    public void setDescription_pa(String description_pa) {this.description_pa = description_pa; }

    @Override
    public String toString() {
        return "Package{" +
                "id_package=" + id_package +
                ", name_pa='" + name_pa + '\'' +
                ", description_pa='" + description_pa + '\'' +
                '}';
    }
}
