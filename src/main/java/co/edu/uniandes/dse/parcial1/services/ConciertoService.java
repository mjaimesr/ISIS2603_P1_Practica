package co.edu.uniandes.dse.parcial1.services;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;

@Slf4j
@Service
public class ConciertoService {

    // Complete
    @Autowired
    ConciertoRepository conciertoRepository;

    @Transactional
    public List<ConciertoEntity> getConciertos() {
        log.info("Inicia proceso de consultar todas las marcas");
        return conciertoRepository.findAll();
    }

    @Transactional
    public ConciertoEntity createMarca(ConciertoEntity concierto) throws IllegalOperationException {
        log.info("Inicia proceso de creación de una nueva marca");

        if (concierto.getNombre() == null || concierto.getNombre().trim().isEmpty()) {
            throw new IllegalOperationException("El nombre del concierto es obligatorio");
        }

        if (concierto.getCapacidad() == null || concierto.getCapacidad() <= 10) {
            throw new IllegalOperationException("el concierto debe tener una capacidad superior a 10 personas");
        }

        if (concierto.getPresupuesto() == null || concierto.getPresupuesto() <= 1000) {
            throw new IllegalOperationException("el presupiuesto dek concierto debe se mayor a 1000 dolares");
        }

        if (concierto.getNombreartista() == null || concierto.getNombreartista().trim().isEmpty()) {
            throw new IllegalOperationException("El nombre del artista es obligatorio");
        }

        if (concierto.getFechaconcierto() != null) {
            Date current = LocalDateTime.now();
            if (concierto.getFechaconcierto().after(current)) {
                throw new IllegalOperationException("El año de fundación debe estar despues de la fecha actual " + current);
            }
        }

        ConciertoEntity created = conciertoRepository.save(concierto);
        log.info("Termina proceso de creación del concierto con id = {}", created.getId());
        return created;
    }


    @Transactional
    public MarcaEntity getMarca(Long marcaId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar la marca con id = {}", marcaId);
        Optional<MarcaEntity> marcaEntity = marcaRepository.findById(marcaId);

        if (marcaEntity.isEmpty())
            throw new EntityNotFoundException(NOMBRE_ENTIDAD, marcaId.toString());

        log.info("Termina proceso de consultar la marca con id = {}", marcaId);
        return marcaEntity.get();
    }

    @Transactional
    public MarcaEntity updateMarca(MarcaEntity marca, Long marcaId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar la marca con id = {}", marcaId);
        Optional<MarcaEntity> marcaEntity = marcaRepository.findById(marcaId);

        if (marcaEntity.isEmpty())
            throw new EntityNotFoundException(NOMBRE_ENTIDAD, marcaId.toString());

        if (marca.getNombre() != null && !marca.getNombre().trim().isEmpty()) {
            List<MarcaEntity> porNombre = marcaRepository.findByNombre(marca.getNombre());
            if (porNombre != null) {
                for (MarcaEntity m : porNombre) {
                    if (!m.getId().equals(marcaId)) {
                        throw new IllegalOperationException("Ya existe otra marca con el nombre " + marca.getNombre());
                    }
                }
            }
        }

        if (marca.getAnoFundacion() != null) {
            int currentYear = Year.now().getValue();
            if (marca.getAnoFundacion() < 1800 || marca.getAnoFundacion() > currentYear) {
                throw new IllegalOperationException("El año de fundación debe estar entre 1800 y " + currentYear);
            }
        }

        marca.setId(marcaId);
        MarcaEntity updated = marcaRepository.save(marca);
        log.info("Termina proceso de actualizar la marca con id = {}", marcaId);
        return updated;
    }

    @Transactional
    public void deleteMarca(Long marcaId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar la marca con id = {}", marcaId);

        MarcaEntity marcaEntity = marcaRepository.findById(marcaId)
                .orElseThrow(() -> new EntityNotFoundException(NOMBRE_ENTIDAD, marcaId.toString()));

        marcaRepository.delete(marcaEntity);
        log.info("Termina proceso de borrar la marca con id = {}", marcaId);
    }
}

