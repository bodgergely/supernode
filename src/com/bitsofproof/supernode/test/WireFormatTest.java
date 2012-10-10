package com.bitsofproof.supernode.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

import org.junit.Test;

import com.bitsofproof.supernode.core.Hash;
import com.bitsofproof.supernode.core.WireFormat;

public class WireFormatTest {

	@Test
	public void testUint16() {
		ByteArrayOutputStream bs = new ByteArrayOutputStream ();
		WireFormat.Writer writer = new WireFormat.Writer(bs);
		writer.writeUint16(21845);
		
		WireFormat.Reader reader = new WireFormat.Reader (bs.toByteArray());
		assertEquals (reader.readUint16(), 21845l);
		assertTrue (reader.eof());
	}

	@Test
	public void testUint32() {
		ByteArrayOutputStream bs = new ByteArrayOutputStream ();
		WireFormat.Writer writer = new WireFormat.Writer(bs);
		writer.writeUint32(0xD9B4BEF9l);
		
		WireFormat.Reader reader = new WireFormat.Reader (bs.toByteArray());
		assertEquals (reader.readUint32(), 0xD9B4BEF9l);
		assertTrue (reader.eof());
	}

	@Test
	public void testUint64() {
		ByteArrayOutputStream bs = new ByteArrayOutputStream ();
		WireFormat.Writer writer = new WireFormat.Writer(bs);
		BigInteger n = new BigInteger ("286331153");
		n = n.multiply(n);
		writer.writeUint64(n);
		
		WireFormat.Reader reader = new WireFormat.Reader (bs.toByteArray());
		assertEquals (reader.readUint64(), n);
		assertTrue (reader.eof());
	}

	@Test
	public void testVarInt() {
		ByteArrayOutputStream bs = new ByteArrayOutputStream ();
		WireFormat.Writer writer = new WireFormat.Writer(bs);
		writer.writeVarInt(286331153);
		writer.writeVarInt(1153);
		writer.writeVarInt(53);
		
		WireFormat.Reader reader = new WireFormat.Reader (bs.toByteArray());
		assertEquals (reader.readVarInt(), 286331153);
		assertEquals (reader.readVarInt(), 1153);
		assertEquals (reader.readVarInt(), 53);
		assertTrue (reader.eof());
	}

	@Test
	public void testReadHash() {
		ByteArrayOutputStream bs = new ByteArrayOutputStream ();
		WireFormat.Writer writer = new WireFormat.Writer(bs);
		Hash h = new Hash ();
		h.digest(new String ("Hello World !").getBytes());
		writer.writeHash(h);
		
		WireFormat.Reader reader = new WireFormat.Reader (bs.toByteArray());
		assertEquals (reader.readHash().toString(), h.toString());
		assertTrue (reader.eof());
	}

	@Test
	public void testString() {
		ByteArrayOutputStream bs = new ByteArrayOutputStream ();
		WireFormat.Writer writer = new WireFormat.Writer(bs);
	
		writer.writeString ("Hello World !");
		
		WireFormat.Reader reader = new WireFormat.Reader (bs.toByteArray());
		assertEquals (reader.readString(), "Hello World !");
		assertTrue (reader.eof());
	}
	
	public String dump(byte [] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		int n = 0;
		for (int i = 0; i < bytes.length; ++i) {
			byte b = bytes [i];
			String s = Integer.toString(0xFF & b, 16);
			if (s.length() < 2)
				buf.append('0');			
			buf.append(s);
			if ( (++n)%16 == 0 )
				buf.append('\n');
		}
		return buf.toString();
	}
}