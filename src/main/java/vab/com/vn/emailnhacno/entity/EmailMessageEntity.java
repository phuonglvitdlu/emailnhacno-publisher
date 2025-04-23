package vab.com.vn.emailnhacno.entity;

import java.io.Serializable;

public class EmailMessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    private String fromEmail;
    private String[] toEmail;
    private String subject;
    private String body;
//    private File headerImg;
//    private File footerImg;

//    public File getFooterImg() {
//        return footerImg;
//    }
//
//    public void setFooterImg(File footerImg) {
//        this.footerImg = footerImg;
//    }
//
//    public File getHeaderImg() {
//        return headerImg;
//    }
//
//    public void setHeaderImg(File headerImg) {
//        this.headerImg = headerImg;
//    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String[] getToEmail() {
        return toEmail;
    }

    public void setToEmail(String[] toEmail) {
        this.toEmail = toEmail;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

}
