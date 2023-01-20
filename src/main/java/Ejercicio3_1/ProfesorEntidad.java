package Ejercicio3_1;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
@Entity
@Table(name="Profesores")
public class ProfesorEntidad implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "idProfesorado")
    private int idProfesorado;
    @Column (name = "Nombre")
    private String Nombre;
    @Column (name = "Apellidos")
    private String Apellidos;
    @Column (name = "FechaNacimiento")
    private Date FechaNacimiento;
    @Column (name = "Antiguedad")
    private int Antiguedad;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "idProfesorado")
    private List<MatriculaEntidad> matriculaEntidad;

    public ProfesorEntidad( String nombre, String apellidos, Date fechaNacimiento, int antiguedad) {
        Nombre = nombre;
        Apellidos = apellidos;
        FechaNacimiento = fechaNacimiento;
        Antiguedad = antiguedad;
    }


    public ProfesorEntidad() {

    }

    public int getIdProfesorado() {
        return idProfesorado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public int getAntiguedad() {
        return Antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        Antiguedad = antiguedad;
    }

    public List<MatriculaEntidad> getMatriculaEntidad() {
        return matriculaEntidad;
    }

    public void setMatriculaEntidad(List<MatriculaEntidad> matriculaEntidad) {
        this.matriculaEntidad = matriculaEntidad;
    }
}
