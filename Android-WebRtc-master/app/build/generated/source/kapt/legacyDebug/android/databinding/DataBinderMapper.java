
package android.databinding;
import com.myhexaville.androidwebrtc.BR;
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 25;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case com.myhexaville.androidwebrtc.R.layout.activity_main:
                    return com.myhexaville.androidwebrtc.databinding.ActivityMainBinding.bind(view, bindingComponent);
                case com.myhexaville.androidwebrtc.R.layout.activity_call:
                    return com.myhexaville.androidwebrtc.databinding.ActivityCallBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case 423753077: {
                if(tag.equals("layout/activity_main_0")) {
                    return com.myhexaville.androidwebrtc.R.layout.activity_main;
                }
                break;
            }
            case 137549018: {
                if(tag.equals("layout/activity_call_0")) {
                    return com.myhexaville.androidwebrtc.R.layout.activity_call;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"};
    }
}