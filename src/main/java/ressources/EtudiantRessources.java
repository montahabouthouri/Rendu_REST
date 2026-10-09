package ressources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("etudiants")
public class EtudiantRessources {

    public static EtudiantBusiness etudiantMetier = new EtudiantBusiness();

    // =====================================================
    // 1) POST /etudiants : créer un étudiant
    // =====================================================
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiantMetier.addEtudiant(etudiant))
            return Response.status(Response.Status.CREATED).entity(etudiant).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    // =====================================================
    // 2) GET /etudiants : liste JSON de tous les étudiants
    // =====================================================
    @GET
    @Produces("application/json")
    public Response getAllEtudiants() {
        List<Etudiant> etudiants = etudiantMetier.getAllEtudiants();
        if (etudiants.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(etudiants).build();
    }

    // =====================================================
    // 3) GET /etudiants/option?codeOption=1 : liste XML
    //    (à placer AVANT /{identifiant})
    // =====================================================
    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = new Option();
        option.setCodeOption(codeOption);
        List<Etudiant> etudiants = etudiantMetier.getEtudiantsByOption(option);
        if (etudiants.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(new EtudiantList(etudiants)).build();
    }

    // =====================================================
    // 4) GET /etudiants/{identifiant} : un étudiant
    // =====================================================
    @GET
    @Path("/{identifiant}")
    @Produces("application/json")
    public Response getEtudiantById(@PathParam("identifiant") String identifiant) {
        Etudiant etudiant = etudiantMetier.getEtudiantByIdentifiant(identifiant);
        if (etudiant != null)
            return Response.status(Response.Status.OK).entity(etudiant).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // =====================================================
    // 5) PUT /etudiants/{identifiant} : modifier
    // =====================================================
    @PUT
    @Path("/{identifiant}")
    @Consumes("application/json")
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant updatedEtudiant) {
        if (etudiantMetier.updateEtudiant(identifiant, updatedEtudiant))
            return Response.status(Response.Status.OK).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // =====================================================
    // 6) DELETE /etudiants/{identifiant} : supprimer
    // =====================================================
    @DELETE
    @Path("/{identifiant}")
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        if (etudiantMetier.deleteEtudiant(identifiant))
            return Response.status(Response.Status.OK).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}