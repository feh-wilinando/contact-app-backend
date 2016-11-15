package br.com.fws.contact_app_backend.resource;

import java.net.URI;
import java.util.List;

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
import br.com.fws.contact_app_backend.model.Contato;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Api("Contatos")
@Path("contatos")
public class ContatoResource {

	@Inject
	private ContatoDao dao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(
			@ApiResponse(	code=200,
							message="Listagem com sucesso",
							response=Contato.class
						)
				 ) 					
	@ApiOperation(value="Listar contatos", produces=MediaType.APPLICATION_JSON)
	public List<Contato> listaTodos(){
		return dao.listAll();
	}
	
	@GET
	@Path("{id:\\d+}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(
			@ApiResponse(	code=200,
							message="Contato listado com sucesso",
							response=Contato.class
						)
				 ) 	
	@ApiOperation(value="Listar contato", produces=MediaType.APPLICATION_JSON)
	public Response porId(@ApiParam(value="ID contato", name="id", required=true) @PathParam("id") Long id){		
		if (!dao.hasId(id)) return Response.noContent().build();
		
		return Response.ok(dao.findById(id)).build();
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	@ApiOperation(value="Criar um contato", consumes=MediaType.APPLICATION_JSON)
	@ApiResponses(
			@ApiResponse(	code=201,
							message="Contato criado", 
							response=Contato.class, 
							responseHeaders=@ResponseHeader(name="Location", description="uri para novo contato", response=String.class)
						)
			)
	public Response incluir(@ApiParam(value="Contato", name="contato", required=true)  Contato contato){
		
		dao.add(contato);
		
		URI uri = URI.create("/contatos/" + contato.getId());
		
		return Response.created(uri).build();
	}
	
	@DELETE
	@Path("id:\\d+")
	@Transactional
	@ApiOperation(value="Remover contato por id")
	@ApiResponses({
				@ApiResponse(code=200, message="Contato removido"),
				@ApiResponse(code=204, message="Contato não encontrado")
	})
	public Response removerPorId(@ApiParam(value="ID contato", name="id", required=true) @PathParam("id") Long id){
		
		Contato contato = dao.findById(id);
		
		if (!dao.hasId(id)) return Response.noContent().build();		
		
		dao.remove(contato);
		
		return Response.ok().build();
	}
	
	@PUT
	@Path("id:\\d+")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	@ApiOperation(value="Atualizar contato com id", produces=MediaType.APPLICATION_JSON, consumes=MediaType.APPLICATION_JSON)
	@ApiResponses({
				@ApiResponse(code=200, message="Contato Atualizado"),
				@ApiResponse(code=204, message="Contato não encontrado")
	})
	public Response atualizar( @ApiParam(value="ID contato", name="id", required=true) @PathParam("id") Long id, 
							   @ApiParam(value="Contato", name="contato", required=true) Contato contato){			
		
		if (!dao.hasId(id)) return Response.noContent().build();
		
		contato.setId(id);
		
		dao.update(contato);
		
		return Response.ok(contato).build();
	}
	
}
