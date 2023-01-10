import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Jugadores")
public class PersonasEntidad implements Serializable {

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumAciertos() {
        return numAciertos;
    }

    public void setNumAciertos(int numAciertos) {
        this.numAciertos = numAciertos;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name = "idJugador")
    private int idJugador;
    @Column (name = "nombre")
    private String nombre;
    @Column (name = "numAciertos")
    private int numAciertos;
    public PersonasEntidad(){

    }

    public PersonasEntidad(String nombre, int numAciertos) {
        this.nombre = nombre;
        this.numAciertos = numAciertos;
    }

}
