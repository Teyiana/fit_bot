package org.tytysh.fit_bot.entity;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.Arrays;

public class CustomLongArrayType implements UserType<Long[]> {

    @Override
    public int getSqlType() {
        return Types.ARRAY;
    }

    @Override
    public Class<Long[]> returnedClass() {
        return Long[].class;
    }

    @Override
    public boolean equals(Long[] x, Long[] y) {
        return Arrays.equals(x, y);
    }

    @Override
    public int hashCode(Long[] x) {
        return Arrays.hashCode(x);
    }

    @Override
    public Long[] nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session,
                              Object owner) throws SQLException {
        Array array = rs.getArray(position);
        return array != null ? (Long[]) array.getArray() : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Long[] value, int index,
                            SharedSessionContractImplementor session) throws SQLException {
        if (st != null) {
            if (value != null) {
                Array array = session.getJdbcConnectionAccess().obtainConnection().createArrayOf("int", value);
                st.setArray(index, array);
            } else {
                st.setNull(index, Types.ARRAY);
            }
        }
    }

    @Override
    public Long[] deepCopy(Long[] value) {
        return Arrays.copyOf(value, value.length);
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Long[] value) {
        return value;
    }

    @Override
    public Long[] assemble(Serializable cached, Object owner) {
        return cached != null ? ((Long[]) cached).clone() : null;
    }

}
