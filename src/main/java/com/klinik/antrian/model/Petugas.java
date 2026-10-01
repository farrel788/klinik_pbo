package com.klinik.antrian.model;

public class Petugas {
    public enum Role { ADMIN, LOKET, POLI }

    private int idPetugas;
    private String username;
    private String password;
    private Role role;

    public Petugas() {}
    public Petugas(String username, String password, Role role) {
        this.username = username; this.password = password; this.role = role;
    }

    public int getIdPetugas()               { return idPetugas; }
    public void setIdPetugas(int id)        { this.idPetugas = id; }
    public String getUsername()             { return username; }
    public void setUsername(String u)       { this.username = u; }
    public String getPassword()             { return password; }
    public void setPassword(String p)       { this.password = p; }
    public Role getRole()                   { return role; }
    public void setRole(Role r)             { this.role = r; }
}