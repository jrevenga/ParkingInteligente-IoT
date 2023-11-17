/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author Esteban G
 */
public class Plaza {
    private int id;
    private String estado;
    private int id_parking;
    
    public Plaza()
    {
        // default
    }
    
    public int gegId()
    {
        return this.id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String gegEstado()
    {
        return this.estado;
    }
    public void setEstado(String estado)
    {
        this.estado = estado;
    }
    public int gegIdParking()
    {
        return this.id_parking;
    }
    public void setIdParking(int id_parking)
    {
        this.id_parking = id_parking;
    }
}
