/**
 * Copyright 2015 Welab, Inc. All rights reserved.
 * WELAB PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ninja.pre;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author <a href="mailto:huan.sheng@wolaidai.com">shenghuan</a>
 */
public class User {
    public enum Gender { MALE, FEMALE };

    public static class Name {
      private String _first, _last;
      @JsonIgnore
      public String getFirst() { return _first; }
      public String getLast() { return _last; }

      public void setFirst(String s) { _first = s; }
      public void setLast(String s) { _last = s; }
    }
    
    private Gender _gender;
    private Name _name;
    private boolean _isVerified;
    
    private byte[] _userImage;

    public Name getName() { return _name; }
    public boolean isVerified() { return _isVerified; }
    public Gender getGender() { return _gender; }
    public byte[] getUserImage() { return _userImage; }

    public void setName(Name n) { _name = n; }
    public void setVerified(boolean b) { _isVerified = b; }
    public void setGender(Gender g) { _gender = g; }
    public void setUserImage(byte[] b) { _userImage = b; }
    
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException
	{
	    	ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
	    	User user = mapper.readValue(new File("src/main/resources/user.json"), User.class);
	    	System.out.println(user.getName().getFirst());
	    	System.out.println(user.getUserImage());
	}
}
