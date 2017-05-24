package com.example.dusanspasic.androidrealmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.dusanspasic.androidrealmdemo.model.Dog;
import com.example.dusanspasic.androidrealmdemo.model.Person;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmList;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RealmAsyncTask mTransaction;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);

        mRealm = Realm.getDefaultInstance();

//        addDog(0, "Rex", 1, "white");
//        addDog(1, "Bruno", 2, "black");
//        addDog(2, "Jacky", 3, "brown");
//
//        RealmList<Dog> dogs = new RealmList<>();
//        dogs.add(getDog(0));
//        dogs.add(getDog(1));
//        addPerson(0, "John", dogs);
//
//        RealmList<Dog> dogs1 = new RealmList<>();
//        dogs1.add(getDog(2));
//        addPerson(1, "Peter", dogs1);

        query1();
        query2();
        query3();
        query4();
        query5();
    }

    private void addDog(final int id, final String name, final int yearsOld, final String color) {

        mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                Dog dog = new Dog();
                dog.setId(id);
                dog.setName(name);
                dog.setAge(yearsOld);
                dog.setColor(color);
                bgRealm.insertOrUpdate(dog);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Dog added successfully");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "Error while adding Dog to database");
                error.printStackTrace();
            }
        });
    }

    private void addPerson(final long id, final String name, final RealmList<Dog> dogs) {
        mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                Person person = new Person();
                person.setId(id);
                person.setName(name);
                person.setDogs(dogs);
                bgRealm.insertOrUpdate(person);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Person added successfully");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "Error while adding Person-s to database");
                error.printStackTrace();
            }
        });
    }

    public Dog getDog(final int id) {
        Dog dog = mRealm.where(Dog.class).equalTo("id", id).findFirst();
        return dog;
    }

    public void query1() {
        RealmResults<Dog> results = mRealm.where(Dog.class)
                .greaterThan("age", 1)
                .findAll();

        for (Dog dog : results) {
            Log.d(TAG, "Query 1: " + dog.getName());
        }
    }

    public void query2() {
        Dog results = mRealm.where(Dog.class).between("age", 2, 4).findFirst();

        Log.d(TAG, "Query 2: " + results.getName());
    }

    public void query3() {
        Person person = mRealm.where(Person.class).equalTo("dogs.age", 2).findFirst();

        Log.d(TAG, "Query 3: " + person.getName());
    }

    public void query4() {
        RealmResults<Person> persons = mRealm.where(Person.class).findAll();

        Person result = new Person();
        for (Person p : persons) {
            if (p.getDogs().size() == 1) {
                result = p;
                break;
            }
        }
        Log.d(TAG, "Query 4: " + result.getName());
    }

    public void query5() {

        mTransaction = mRealm.executeTransactionAsync(new Realm.Transaction() {
            final Person person = mRealm.where(Person.class).equalTo("id", 1).findFirst();
            Person newPerson = mRealm.copyFromRealm(person);

            @Override
            public void execute(Realm bgRealm) {

                newPerson.setName("Zoky");
                bgRealm.insertOrUpdate(newPerson);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Person updated successfully");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "Error while updating Person-s to database");
                error.printStackTrace();
            }
        });
    }

    public void deleteAllDogs() {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Dog.class);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Successfully deleted all dogs");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, "Error while deleting dogs table in database");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!mRealm.isClosed() && mRealm != null) {
            mRealm.close();
        }

        if (mTransaction != null && !mTransaction.isCancelled()) {
            mTransaction.cancel();
        }
    }
}
