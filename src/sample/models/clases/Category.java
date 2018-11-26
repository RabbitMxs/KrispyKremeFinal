package sample.models.clases;

public class Category {
    int id_category;
    String name;

    public Category(int id_category, String name) {
        this.id_category = id_category;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_category() { return id_category; }

    public void setId_category(int id_category) { this.id_category = id_category; }

    @Override
    public String toString() {
        return "Category{" +
                "id_category=" + id_category +
                ", name='" + name + '\'' +
                '}';
    }
}
