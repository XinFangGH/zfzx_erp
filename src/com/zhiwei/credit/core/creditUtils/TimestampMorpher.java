package com.zhiwei.credit.core.creditUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import net.sf.ezmorph.object.AbstractObjectMorpher;

public class TimestampMorpher extends AbstractObjectMorpher {

	private String[] formats;
	
	public TimestampMorpher(String[] formats){
		this.formats = formats ;
	}
	@Override
	public Object morph(Object value) {
		if (value == null||"".equals(value)) {
			return null;
		}
		
		if (Timestamp.class.isAssignableFrom(value.getClass())) {
			return (Timestamp) value;
		}
		String strValue = (String) value;
		SimpleDateFormat dateParser = null;
		for (int i = 0; i < formats.length; i++) {
			if (dateParser == null) {
				dateParser = new SimpleDateFormat(formats[i]);
			} else {
				dateParser.applyPattern(formats[i]);
			}
			try {
				return new Timestamp(dateParser.parse(strValue.toLowerCase()).getTime());
			} catch (ParseException pe) {
				// ignore exception, try the next format
			}
		}
		return null;
	}

	@Override
	public Class morphsTo() {
		return Timestamp.class;
	}

}
