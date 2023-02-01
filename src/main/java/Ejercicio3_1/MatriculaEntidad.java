package Ejercicio3_1;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="Matrícula")
public class MatriculaEntidad implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idMatricula;
    @ManyToOne
    @JoinColumn(name="idAlumnado")
    private AlumnadoEntidad Alumnado;
    @ManyToOne
    @JoinColumn(name="idProfesorado")
    private ProfesorEntidad Profesorado;
    @Column (name = "Asignatura")
    private String  Asignatura;
    @Column (name = "Curso")
    private int Curso;


    public MatriculaEntidad( AlumnadoEntidad alumnado, ProfesorEntidad profesorado, String asignatura, int curso) {
        Alumnado = alumnado;
        Profesorado = profesorado;
        Asignatura = asignatura;
        Curso = curso;
    }

    public MatriculaEntidad() {

    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public AlumnadoEntidad getAlumnado() {
        return Alumnado;
    }

    public void setAlumnado(AlumnadoEntidad alumnado) {
        Alumnado = alumnado;
    }

    public ProfesorEntidad getProfesorado() {
        return Profesorado;
    }

    public void setProfesorado(ProfesorEntidad profesorado) {
        Profesorado = profesorado;
    }

    public String getAsignatura() {
        return Asignatura;
    }

    public void setAsignatura(String asignatura) {
        Asignatura = asignatura;
    }

    public int getCurso() {
        return Curso;
    }

    public void setCurso(int curso) {
        Curso = curso;
    }
}
