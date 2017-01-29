/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.socialhive.rssninja.models;

import com.sun.istack.internal.NotNull;
import java.io.Serializable;

/**
 *
 * @author Munsch
 */

public class RSSFeed implements Serializable {
    Long id;

    @NotNull
    private String name;

    @NotNull
    private String url;

    @NotNull
    private Long owner_id;

    public RSSFeed() {
    }

    public RSSFeed(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
    }

    public String toString() { return name; }
}