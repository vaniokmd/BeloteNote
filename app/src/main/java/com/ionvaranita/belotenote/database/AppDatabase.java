package com.ionvaranita.belotenote.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.ionvaranita.belotenote.dao.Joc4JucatoriInEchipaDao;
import com.ionvaranita.belotenote.dao.PuncteCastigatoareGlobalDao;
import com.ionvaranita.belotenote.dao.TabellaPunti4GiocatoriInSquadraDao;
import com.ionvaranita.belotenote.dao.PuncteCastigatoare4JucatoriInEchipaDao;
import com.ionvaranita.belotenote.dao.Scor4JucatoriInEchipaDao;
import com.ionvaranita.belotenote.dao.TurnManagement4GiocatoriInSquadraDao;
import com.ionvaranita.belotenote.entity.Gioco4GiocatoriInSquadra;
import com.ionvaranita.belotenote.entity.PuncteCastigatoareGlobalBean;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.entity.PuncteCastigatoare4JucatoriInEchipaBean;
import com.ionvaranita.belotenote.entity.Scor4JucatoriInEchipaEntityBean;
import com.ionvaranita.belotenote.entity.TurnManagement4GiocatoriInSquadra;

/**
 * Created by ionvaranita on 20/11/17.
 */
@Database(entities = {TurnManagement4GiocatoriInSquadra.class
        ,Punti4GiocatoriInSquadraEntityBean.class, PuncteCastigatoareGlobalBean.class, Scor4JucatoriInEchipaEntityBean.class, PuncteCastigatoare4JucatoriInEchipaBean.class,Gioco4GiocatoriInSquadra.class}, version = 16)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "BeloteNoteDatabase";

    private static AppDatabase INSTANCE;
    private static AppDatabase PERSISTENT_DATABASE;

    public abstract TurnManagement4GiocatoriInSquadraDao turnManagement4GiocatoriInSquadraDao();

    public abstract TabellaPunti4GiocatoriInSquadraDao tabellaPunti4GiocatoriInSquadraDao();

    public abstract Scor4JucatoriInEchipaDao scor4JucatoriInEchipaDao();

    public abstract Joc4JucatoriInEchipaDao joc4JucatoriInEchipaDao();

    public abstract PuncteCastigatoare4JucatoriInEchipaDao puncteCastigatoare4JucatoriInEchipaDao();

    public abstract PuncteCastigatoareGlobalDao puncteCastigatoareGlobalDao();

  /*  public abstract UserDao userModel();

    public abstract BookDao bookModel();

    public abstract LoanDao loanModel();*/

    private static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                            // To simplify the codelab, allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }
    public static AppDatabase getPersistentDatabase(Context context){
        if(PERSISTENT_DATABASE==null){
            boolean condizione = true;

            while (condizione){
                try {
                    Log.d(DATABASE_NAME,"First creation of database");
                    PERSISTENT_DATABASE = Room.databaseBuilder(context,AppDatabase.class,DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

                    PuncteCastigatoareGlobalBean puncteCastigatoareGlobalBean1 = new PuncteCastigatoareGlobalBean();
                    PuncteCastigatoareGlobalBean puncteCastigatoareGlobalBean2 = new PuncteCastigatoareGlobalBean();

                    puncteCastigatoareGlobalBean1.setPuncteCastigatoare(101);
                    puncteCastigatoareGlobalBean2.setPuncteCastigatoare(51);


                    PERSISTENT_DATABASE.puncteCastigatoareGlobalDao().insertPuncteCastigatoareGlobal(puncteCastigatoareGlobalBean1);
                    PERSISTENT_DATABASE.puncteCastigatoareGlobalDao().insertPuncteCastigatoareGlobal(puncteCastigatoareGlobalBean2);
                    condizione = false;
                }catch (Exception e){
                    Log.d("database error!",e.getMessage());
                    Room.databaseBuilder(context,AppDatabase.class,DATABASE_NAME).fallbackToDestructiveMigration();
                }
            }
        }
        return PERSISTENT_DATABASE;
    }

    public static void destroyInstanceInMemoryDatabase() {
        INSTANCE = null;
    }
}