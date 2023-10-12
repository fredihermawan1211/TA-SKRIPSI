package com.api.ponline.controllers;

import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.mapping.Any;
import org.jvnet.hk2.annotations.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ponline.dao.Request.KomunitasRequest;
import com.api.ponline.dao.Request.ScimRequest;

@RestController
@RequestMapping("/scim")
public class ScimController {

    @GetMapping("aadOptscim062020/Users")
    public ResponseAz createUser(@RequestBody(required = false) ScimRequest ScimRequest) {
        
        ResponseAz responseAz = new ResponseAz();
        String[] arr = {"urn:ietf:params:scim:api:messages:2.0:PatchOp"};
        responseAz.setSchemas(arr);
        Operations operations = new Operations();
        operations.setOp("Replace");
        operations.setPath("active");
        operations.setValue("false");
        responseAz.setOperations(operations);

        return responseAz;
    }
    
    

    public class ResponseAz {
        private String[] schemas;
        private Operations Operations;

        public String[] getSchemas() {
            return schemas;
        }
        public void setSchemas(String[] schemas) {
            this.schemas = schemas;
        }
        public Operations getOperations() {
            return Operations;
        }
        public void setOperations(Operations operations) {
            Operations = operations;
        } 
        
        
    }

    public class Operations {        
        private String op;
        private String path;
        private String value;

        public String getOp() {
            return op;
        }
        public void setOp(String op) {
            this.op = op;
        }
        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            this.path = path;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }

        
    }
}
