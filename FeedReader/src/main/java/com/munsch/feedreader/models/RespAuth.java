/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.munsch.feedreader.models;

import java.io.Serializable;

/**
 *
 * @author Munsch
 */
public class RespAuth implements Serializable {
        public Long id;
        public String token;
        public String email;

        public RespAuth() {}

        public RespAuth(Long id, String token, String email) {
            this.id = id;
            this.token = token;
            this.email = email;
        }

        public RespAuth(Long id, String token) {
            this.id = id;
            this.token = token;
        }
    }