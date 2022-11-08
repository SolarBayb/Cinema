package lpnu.model;

public enum EnumTechnology {
    TwoD("2D",0),
    ThreeD("3D",40),
    FourDX("4D",90);

    private final String name;
    private final double price;

    EnumTechnology(final String name, final double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
