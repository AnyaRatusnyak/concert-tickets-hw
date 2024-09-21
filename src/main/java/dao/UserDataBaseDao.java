package dao;

import model.UserDataBase;

public interface UserDataBaseDao {
    UserDataBase save (UserDataBase userDataBase);
    UserDataBase get (Long id);
    boolean delete (Long id);
}
