/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vab.com.vn.emailnhacno.entity;

import javax.persistence.*;

/**
 *
 * @author LAMNTP
 */
@Entity
@Table(name = "N_MAILTEMPLATE")
public class MailTemplate {

    @Id
    @Column(name = "IDTEMPLATE")
    private String IDTEMPLATE;
//    private int TYPE;
    private String TITLE;
    @Lob
    private String CONTENT;
    private String IMG_HEADER;
    private String IMG_FOOTER;
    private String COMPONENT;

    public String getCOMPONENT() {
        return COMPONENT;
    }

    public void setCOMPONENT(String COMPONENT) {
        this.COMPONENT = COMPONENT;
    }

    /**
     * @return the IDTEMPLATE
     */
    public String getIDTEMPLATE() {
        return IDTEMPLATE;
    }

    /**
     * @param IDTEMPLATE the IDTEMPLATE to set
     */
    public void setIDTEMPLATE(String IDTEMPLATE) {
        this.IDTEMPLATE = IDTEMPLATE;
    }

    /**
     * @return the TITLE
     */
    public String getTITLE() {
        return TITLE;
    }

    /**
     * @param TITLE the TITLE to set
     */
    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    /**
     * @return the CONTENT
     */
    public String getCONTENT() {
        return CONTENT;
    }

    /**
     * @param CONTENT the CONTENT to set
     */
    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    /**
     * @return the IMG_HEADER
     */
    public String getIMG_HEADER() {
        return IMG_HEADER;
    }

    /**
     * @param IMG_HEADER the IMG_HEADER to set
     */
    public void setIMG_HEADER(String IMG_HEADER) {
        this.IMG_HEADER = IMG_HEADER;
    }

    /**
     * @return the IMG_FOOTER
     */
    public String getIMG_FOOTER() {
        return IMG_FOOTER;
    }

    /**
     * @param IMG_FOOTER the IMG_FOOTER to set
     */
    public void setIMG_FOOTER(String IMG_FOOTER) {
        this.IMG_FOOTER = IMG_FOOTER;
    }
    
}
