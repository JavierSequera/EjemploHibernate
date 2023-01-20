package Ejercicio3_1;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
@Entity
@Table(name="Alumnos")
public class AlumnadoEntidad implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "idAlumnado")
    private int idAlumnado;
    @Column (name = "Nombre")
    private String Nombre;
    @Column (name = "Apellidos")
    private String Apellidos;
    @Column (name = "FechaNacimiento")
    private Date FechaNacimiento;
    @Column (name = "Antiguedad")
    private int Antiguedad;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "idAlumnado")
    private List<MatriculaEntidad> matriculaEntidad;


    public AlumnadoEntidad(String Nombre, String Apellido, Date FechaNac, int Antiguedad) {
        this.Nombre = Nombre;
        this.Apellidos = Apellido;
        this.FechaNacimiento = FechaNac;
        this.Antiguedad = Antiguedad;
    }

    public AlumnadoEntidad() {

    }

    public int getIdAlumnado() {
        return idAlumnado;
    }

    public void setIdAlumnado(int idAlumnado) {
        this.idAlumnado = idAlumnado;
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
