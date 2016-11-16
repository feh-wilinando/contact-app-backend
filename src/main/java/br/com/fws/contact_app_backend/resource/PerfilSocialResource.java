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
import br.com.fws.contact_app_backend.dao.PerfilSocialDao;
import br.com.fws.contact_app_backend.model.Contato;
import br.com.fws.contact_app_backend.model.PerfilSocial;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Api("Perfis Sociais")
@Path("contatos/{id:\\d+}/perfis-sociais")
public class PerfilSocialResource {
	
	@Inject private ContatoDao dao;
	@Inject private PerfilSocialDao perfilSocialDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiResponses(
			@ApiResponse(	code=200,
							message="Listagem com sucesso",
							response=PerfilSocial.class
						)
				 ) 					
	@ApiOperation(value="Listar perfis sociais do contato", produces=MediaType.APPLICATION_JSON)
	public Response listar(@ApiParam(value="ID contato", name="id", required=true) @PathParam("id") Long id){
		
		if (!dao.hasId(id)) return Response.noContent().build();
		
		List<PerfilSocial> perfisSociais = dao.findById(id).getPerfisSociais();
				
		return Response.ok(perfisSociais).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	@ApiOperation(value="Adicionar prefil social ao contato", consumes=MediaType.APPLICATION_JSON)
	@ApiResponses({
			@ApiResponse(	code=201,
							message="Perfil adicionado", 
							response=Contato.class, 
							responseHeaders=@ResponseHeader(name="Location", description="uri para novo perfil do contato", response=String.class)
						),
			@ApiResponse(	code=204, message="Contato não encontrado" )
	})
	public Response adicionar( @ApiParam(value="ID contato", name="id", required=true) @PathParam("id") Long id,
							   @ApiParam(value="Perfil Social", name="perfilSocial", required=true) PerfilSocial perfilSocial){
		
		if (!dao.hasId(id)) return Response.noContent().build();
		
		perfilSocialDao.add(perfilSocial);
		
		Contato contato = dao.findById(id);
		contato.add(perfilSocial);
		
		dao.update(contato);
		
		URI uri = URI.create("contatos/" + id + "/telefones/" + perfilSocial.getId());
		
		return Response.created(uri).build();
	}
	
	@DELETE
	@Path("{idPerfilSocial:\\d+}")
	@Transactional
	@ApiOperation(value="Remover perfil social do contato")
	@ApiResponses({
				@ApiResponse(code=200, message="Perfil removido"),
				@ApiResponse(code=204, message="Contato ou perfil social não encontrado")
	})
	public Response remover( @ApiParam(value="ID contato", name="id", required=true) @PathParam("id") Long id, 
							 @ApiParam(value="ID Perfil Social", name="idPerfilSocial", required=true) @PathParam("idPerfilSocial") Long idPerfilSocial){
		
		if (!dao.hasId(id)) return Response.noContent().build();
		
		Contato contato = dao.findById(id);

		List<PerfilSocial> perfisSociais = contato.getPerfisSociais();
		
		Optional<PerfilSocial> optionalPerfilSocial = perfisSociais
												.stream()
													.filter(p -> p.getId().equals(idPerfilSocial))
														.findFirst();
			
		if (optionalPerfilSocial.isPresent()) {
			
			PerfilSocial perfilSocial = optionalPerfilSocial.get();
			
			contato.remove(perfilSocial);
			
			perfilSocialDao.delete(perfilSocial);
			
			dao.update(contato);
			
			return Response.ok().build();
		}else{
			return Response.noContent().build();
		}
		
	}
	
	@PUT
	@Path("{idPerfilSocial:\\d+}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	@ApiOperation(value="Atualizar perfil social do contato", consumes=MediaType.APPLICATION_JSON, produces=MediaType.APPLICATION_JSON)
	@ApiResponses({
				@ApiResponse(code=200, message="Perfil atualizado"),
				@ApiResponse(code=204, message="Contato ou perfil não encontrado")
	})
	public Response atualizar( 	@ApiParam(value="ID contato", name="id", required=true) @PathParam("id") Long id, 
								@ApiParam(value="ID Perfil Social", name="idPerfilSocial", required=true) @PathParam("idPerfilSocial") Long idPerfilSocial, 
								@ApiParam(value="Perfil Social", name="perfilSocial", required=true) PerfilSocial perfilSocial){
		
		if (!dao.hasId(id)) return Response.noContent().build();
		
		Contato contato = dao.findById(id);
		
		List<PerfilSocial> perfisSociais = contato.getPerfisSociais();
		
		Optional<PerfilSocial> optionalPerfilSocial = perfisSociais
															.stream()
																.filter(t -> t.getId().equals(idPerfilSocial))
																	.findFirst();
		
		if (optionalPerfilSocial.isPresent()) {
			
			perfilSocial.setId(idPerfilSocial);
			
			perfilSocialDao.update(perfilSocial);
			
			return Response.ok(perfilSocial).build();
		}else{
			return Response.noContent().build();
		}
		
		
	}
	
}
