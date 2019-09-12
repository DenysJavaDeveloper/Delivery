package project.delivery.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class JsonbType implements UserType {

	private static final ObjectMapper MAPPER = JacksonConfiguration.mapper();

	@Override
	public int[] sqlTypes() {
		return new int[]{Types.JAVA_OBJECT};
	}

	@Override
	public Object nullSafeGet(
		final ResultSet rs,
		final String[] names,
		final SharedSessionContractImplementor session,
		final Object owner
	) throws HibernateException, SQLException {
		final String json = rs.getString(names[0]);
		if (json == null)
			return null;

		try {
			return MAPPER.readValue(json.getBytes(StandardCharsets.UTF_8), returnedClass());
		} catch (final Exception e) {
			throw new RuntimeException(
				"Failed to convert String to " + returnedClass().getName() + ": " + e.getMessage(),
				e
			);
		}
	}

	@Override
	public void nullSafeSet(
		final PreparedStatement st,
		final Object value,
		final int index,
		final SharedSessionContractImplementor session
	) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.OTHER);
			return;
		}

		try {
			final StringWriter w = new StringWriter();
			MAPPER.writeValue(w, value);
			w.flush();
			st.setObject(index, w.toString(), Types.OTHER);
		} catch (final Exception e) {
			throw new RuntimeException(
				"Failed to convert " + returnedClass().getName() + " to String: " + e.getMessage(),
				e
			);
		}
	}

	@Override
	public Object deepCopy(final Object value) throws HibernateException {
		if (value == null)
			return null;

		try {
			// use serialization to create a deep copy
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(value);
			oos.flush();
			oos.close();
			bos.close();

			ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
			return new ObjectInputStream(bais).readObject();
		} catch (ClassNotFoundException | IOException ex) {
			throw new HibernateException(ex);
		}
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(final Object value) throws HibernateException {
		return (Serializable) this.deepCopy(value);
	}

	@Override
	public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
		return this.deepCopy(cached);
	}

	@Override
	public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
		return this.deepCopy(original);
	}

	@Override
	public boolean equals(final Object obj1, final Object obj2) throws HibernateException {
		if (obj1 == null) {
			return obj2 == null;
		}
		return obj1.equals(obj2);
	}

	@Override
	public int hashCode(final Object obj) throws HibernateException {
		return obj.hashCode();
	}
}
