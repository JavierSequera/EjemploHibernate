package Ejercicio3_1;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="Matrícula")
public class MatriculaEntidad implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "idMatricula")
    private int idMatricula;
    @ManyToOne
    @JoinColumn(name="idAlumnado")
    private AlumnadoEntidad Alumnado;
    @ManyToOne
    @JoinColumn(name="idProfesorado")
    private AlumnadoEntidad Profesorado;
    @Column (name = "Asignatura")
    private String  Asignatura;
    @Column (name = "Curso")
    private int Curso;

    public MatriculaEntidad(int idMatricula, AlumnadoEntidad alumnado, AlumnadoEntidad profesorado, String asignatura, int curso) {
        this.idMatricula = idMatricula;
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

    public AlumnadoEntidad getProfesorado() {
        return Profesorado;
    }

    public void setProfesorado(AlumnadoEntidad profesorado) {
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
