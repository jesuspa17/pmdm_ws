package com.dam.salesianostriana.calccalorias;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.dam.salesianostriana.calccalorias.Usuario;
import com.dam.salesianostriana.calccalorias.Ruta;

import com.dam.salesianostriana.calccalorias.UsuarioDao;
import com.dam.salesianostriana.calccalorias.RutaDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig usuarioDaoConfig;
    private final DaoConfig rutaDaoConfig;

    private final UsuarioDao usuarioDao;
    private final RutaDao rutaDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        usuarioDaoConfig = daoConfigMap.get(UsuarioDao.class).clone();
        usuarioDaoConfig.initIdentityScope(type);

        rutaDaoConfig = daoConfigMap.get(RutaDao.class).clone();
        rutaDaoConfig.initIdentityScope(type);

        usuarioDao = new UsuarioDao(usuarioDaoConfig, this);
        rutaDao = new RutaDao(rutaDaoConfig, this);

        registerDao(Usuario.class, usuarioDao);
        registerDao(Ruta.class, rutaDao);
    }
    
    public void clear() {
        usuarioDaoConfig.getIdentityScope().clear();
        rutaDaoConfig.getIdentityScope().clear();
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public RutaDao getRutaDao() {
        return rutaDao;
    }

}
