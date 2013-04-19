package com.example.usersdb.usuarios;

import java.util.Date;

public class Usuario {

	protected long mId;
	protected String mNombre;
	protected String mApellidos;
	protected Date mFechaNacimiento;
	public long getmId() {
		return mId;
	}
	public void setmId(long mId) {
		this.mId = mId;
	}
	public String getmNombre() {
		return mNombre;
	}
	public void setmNombre(String mNombre) {
		this.mNombre = mNombre;
	}
	public String getmApellidos() {
		return mApellidos;
	}
	public void setmApellidos(String mApellidos) {
		this.mApellidos = mApellidos;
	}
	public Date getmFechaNacimiento() {
		return mFechaNacimiento;
	}
	public void setmFechaNacimiento(Date mFechaNacimiento) {
		this.mFechaNacimiento = mFechaNacimiento;
	}
	
	public String toString(){
		return "[{"+mId+"},{"+mNombre+"},{"+mApellidos+"},{"+ mFechaNacimiento.toString()+"}]";
	}
	
	
}
