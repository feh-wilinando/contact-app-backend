package br.com.fws.contact_app_backend.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.fws.contact_app_backend.dao.ContatoDao;
import br.com.fws.contact_app_backend.dao.TelefoneDao;
import br.com.fws.contact_app_backend.model.Contato;
import br.com.fws.contact_app_backend.model.Telefone;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Api("Telefones")
@Path("contatos/{id}/telefones")
public class TelefoneResource {
	
	@Inject private ContatoDao dao;
	@Inject private TelefoneDao telefoneDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(
		@ApiResponse(	code=200,
						message="Listagem com sucesso",
						response=Telefone.class
					)
	) 
						
	@ApiOperation(value="Listar telefones do contato", produces=MediaType.APPLICATION_JSON)
	public Response listarTelefones(@ApiParam(value="ID do contato", name="id", required=true) @PathParam("id") Long id){
		
		if (!dao.hasId(id)) return Response.noContent().build();
		
		List<Telefone> telefones = dao.findById(id).getTelefones();
				
		return Response.ok(telefones).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	@ApiOperation(value="Adicionar telefone ao contato", consumes=MediaType.APPLICATION_JSON)
	@ApiResponses({
			@ApiResponse(	code=201,
							message="Telefone adicionado", 
							response=Contato.class, 
							responseHeaders=@ResponseHeader(name="Location", description="uri para novo telefone do contato", response=String.class)
						),
			@ApiResponse(	code=204, message="Contato não encontrado" )
	})
	public Response adicionar(	@ApiParam(value="ID do contato", name="id", required=true) @PathParam("id") Long id ,
								@ApiParam(value="Telefone", name="telefone", required=true) Telefone telefone){
		
		if (!dao.hasId(id)) return Response.noContent().build();
		
		Contato contato = dao.findById(id);
		contato.add(telefone);
		
		dao.update(contato);
		
		URI uri = URI.create("contatos/" + id + "/telefones/" + telefone.getId());
		
		return Response.created(uri).build();
	}
	
	@DELETE
	@Path("idTelefone:\\d+")
	@Transactional
	@ApiOperation(value="Remover telefone do contato")
	@ApiResponses({
				@ApiResponse(code=200, message="Telefone removido"),
				@ApiResponse(code=204, message="Contato ou telefone não encontrado")
	})
	public Response remover( @ApiParam(value="ID do contato", name="id", required=true) @PathParam("id") Long id,
							 @ApiParam(value="ID Telefone", name="idTelefone", required=true) @PathParam("idTelefone") Long idTelefone){
		
		if (!dao.hasId(id)) return Response.noContent().build();
		
		Contato contato = dao.findById(id);

		List<Telefone> telefones = contato.getTelefones();
		
		Optional<Telefone> optionalTelefone = telefones
												.stream()
													.filter(t -> t.getId().equals(idTelefone))
														.findFirst();
			
		if (optionalTelefone.isPresent()) {
			
			telefoneDao.delete(optionalTelefone.get());
			
			return Response.ok().build();
		}else{
			return Response.noContent().build();
		}
		
	}
	
	@PUT
	@Path("idTelefone:\\d+")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	@ApiOperation(value="Atualizar telefone do contato", consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	@ApiResponses({
				@ApiResponse(code=200, message="Telefone atualizado"),
				@ApiResponse(code=204, message="Contato ou perfil não encontrado")
	})
	public Response atualizar(	@ApiParam(value="ID do contato", name="id", required=true) @PathParam("id") Long id, 
								@ApiParam(value="ID Telefone", name="idTelefone", required=true) @PathParam("idTelefone") Long idTelefone, 
								@ApiParam(value="Telefone", name="telefone", required=true) Telefone telefone){
		
		if (!dao.hasId(id)) return Response.noContent().build();
		
		Contato contato = dao.findById(id);
		
		List<Telefone> telefones = contato.getTelefones();
		
		Optional<Telefone> optionalTelefone = telefones
												.stream()
													.filter(t -> t.getId().equals(idTelefone))
														.findFirst();
		
		if (optionalTelefone.isPresent()) {
			
			telefone.setId(idTelefone);
			
			telefoneDao.update(telefone);
			
			return Response.ok().build();
		}else{
			return Response.noContent().build();
		}
		
		
	}
}
