package org.bedu.realmexample;

import android.app.Application;

import java.util.LinkedHashMap;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication extends Application {

    /*@Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        final Map<String, String> map = new LinkedHashMap();
        map.put("Key1", "value1");
        map.put("Key2", "value2");
        RealmConfiguration config = new RealmConfiguration.Builder().initialData(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                int i = 1;
                for (Map.Entry entry : map.entrySet()) {
                    Contact c = realm.createObject(Contact.class, i++);
                    c.setName((String) entry.getKey());
                }
            }
        }).deleteRealmIfMigrationNeeded().name("realm.db").build();
        Realm.setDefaultConfiguration(config);
    }*/

}
