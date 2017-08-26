package com.hoanganhtuan95ptit.awesomekeyboard.layout.view.callback;

/**
 * Created by durbinlabs on 8/26/17.
 */

public abstract class KeyboardDataPassing {

    public String url ;
    public int position;
    KeyboardDataPassing(String a, int b){
        url = a;
        position = b;
    }
    public void set_date(String url1, int position1)
    {
        url = url1;
        position = position1;
    }
    public String get_utl()
    {
        return  url;
    }
    public int get_int()
    {
        return position;
    }
}
