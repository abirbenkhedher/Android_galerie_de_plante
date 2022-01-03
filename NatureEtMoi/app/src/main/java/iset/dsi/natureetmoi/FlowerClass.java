package iset.dsi.natureetmoi;

public class FlowerClass {
    String nom,desc,croi,cons,uri;
    Boolean fav;


    public String getNom() {
        return nom;
    }

    public String getDesc() {
        return desc;
    }

    public String getCroi() {
        return croi;
    }

    public String getCons() {
        return cons;
    }

    public boolean isFav() {
        return fav;
    }

    public String getUri() {
        return uri;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCroi(String croi) {
        this.croi = croi;
    }

    public void setCons(String cons) {
        this.cons = cons;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
