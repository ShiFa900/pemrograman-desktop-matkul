
public class Menu {

    private int id;
    private String nama;
    private long harga;
    private TipeMenu kategori;

    public Menu(int id, String nama, long harga, TipeMenu kategori) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public long getHarga() {
        return harga;
    }

    public TipeMenu getKategori() {
        return kategori;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(long harga) {
        this.harga = harga;
    }

    public void setKategori(TipeMenu kategori) {
        this.kategori = kategori;
    }

}
