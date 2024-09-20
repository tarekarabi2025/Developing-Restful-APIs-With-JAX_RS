package Model;

//  this class made for explain how hateoas work and put the attributes to the links that we want it to be shown in the response

public class Link {
    private String link ;
    private String rel ;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }
}
