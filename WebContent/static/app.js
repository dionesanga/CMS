// create the module and name it scotchApp
var scotchApp = angular.module("scotchApp", ["ngRoute"]);

// configure our routes
scotchApp.config(function($routeProvider) {		
	$routeProvider
		
	// route for the home page
	.when('/', {
		templateUrl : 'pages/home.html',
		controller  : 'mainController'
	})
	// route for the gerar respostas
	.when('/gerarRespostas', {
		templateUrl : 'pages/IGerarRespostas.html',
		controller  : 'GerarRespostaController'
	})

	// route for the fechar massivo
	.when('/fecharMassivo', {
		templateUrl : 'pages/IFecharMassivo.html',
		controller  : 'FecharContumazAnatel'
	})
	
	// route for the carga mis MIS_ANATEL_FOCUS
	.when('/cargaMis', {
		templateUrl : 'pages/ICargaMis.html',
		controller  : 'CargaMisController'
	})
	
	// route for the ids TRIMESTRE IDA
	.when('/gerarTrimestre', {
		templateUrl : 'pages/IGerarTrimestre.html',
		controller  : 'gerarTrimestreController'
	})

	// route for the carga IDS ANATEL
	.when('/cargaIDsAnatel', {
		templateUrl : 'pages/ICargaIDSAnatel.html',
		controller  : 'cargaIdsAnatelController'
	})

	//
	.when('/roteamento', {
		templateUrl : 'pages/IRoteamentos.html',
		controller  : 'RoteamentoController'
	});
});
	
//file-reader
scotchApp.directive('fileReader', function() {
	return {
		scope: {
			fileReader:"="
		},
		link: function(scope, element) {
			$(element).on('change', function(changeEvent) {
				var files = changeEvent.target.files;
				if (files.length) {
					var r = new FileReader();
					r.onload = function(e) {
						var contents = e.target.result;
						scope.$apply(function () {
							scope.fileReader = contents;
						});
					};
					r.readAsText(files[0]);
				}
			});
		}
	};
});
	
	
// create the controller and inject Angular's $scope
scotchApp.controller('mainController', function($scope) {
	// create a message to display in our view
	$scope.message = 'Everyone come and see how good I look!';
});

scotchApp.controller("GerarRespostaController", function($scope, $http) {
	var self 				= this;
	$scope.vShowMessage 	= false;
	
	self.callProcGerarResposta = function(){
		
		var metodo = 'GET';
		
		$http({
			method: metodo,
			url: "http://sv2kprel3:7001/CMS/rest/gerarResposta/exibir",
			data: null
		}).then(function sucessCallback(response){
			$scope.vShowMessage 	= false;
			$scope.vMessage 		= "Executado com sucesso!";
			self.sucesso();
		}, function errorCallback(response) {
			$scope.vShowMessage 	= true;
			$scope.vMessage 		= "Ocorreu um erro!";
			self.ocorreuErro();				
		});
	};
	
	self.ocorreuErro = function() {
		alert('Ocorreu um erro!');
	};
	
	self.sucesso = function() {
		alert('Executado com sucesso!');
	};
});

scotchApp.controller('cargaIdsAnatelController', function($scope, $http){
	var self 				= this;
	
	self.ids 				= [];
	self.idsList			= new Array();
	self.idTemp				= {};
	self.textIsValid		= '';
	
	$scope.showBtnLoad		= true;
	$scope.showBtnValida	= false;
	$scope.showBtnExecute	= false;
	$scope.showBtnCancel	= false;
	$scope.showTextArea		= true;
	
	self.callStartCargaAnatel = function(){
		self.disableMessages();
		$http({
			method: "POST",
			url: "http://sv2kprel3:7001/CMS/rest/cargaAnatelAutomidia/insertIdsCargaAnatel",
			data: self.idsList
		})
		.then(function sucessCallback(response){
			$scope.vMessage 		= 'Carregado com sucesso!';
			$scope.vShowMessage		= true;
			self.cancelCarga();
		}
		, function errorCallback(response){
			self.cancelCarga();
			$scope.vMessage 		= 'Erro ou Job sendo executado!';
			$scope.vShowMessageError = true;
		});
		
	};
	
	self.callValidarIDs = function(){
		var jsonString = JSON.stringify(self.idsList);
		$http({
			method: "GET",
			url: "http://sv2kprel3:7001/CMS/rest/cargaAnatelAutomidia/callValidarIDs",
			params: {idCanal : jsonString}
		})
		.then(function sucessCallback(response){
			self.idsList			= response.data;
			
			$scope.showBtnValida	= false;
			$scope.showBtnExecute	= true;
			$scope.showBtnCancel	= true;
		}
		, function errorCallback(response){
			alert('error');
			self.cancelCarga();
		});
	};
	
	self.carregarTable = function(){
		self.disableMessages();
		var lines 			= self.ids.split(",");
		self.idsList		= [];
		
		for (i = 0; i < lines.length; i++) {
			self.idTemp								= {};
			if(lines[i]){
				self.idTemp.id 						= lines[i];
				self.idTemp.ultRequest				= {};
				self.idTemp.ultRequest.request		= '';
				self.idTemp.ultRequest.dtFechamento	= '';
				self.idTemp.existMis				= false;
				
				self.idsList.push(self.idTemp);
				if(self.idsList.length === 100){
					alert('Processo limitado a 100 registros!');
					break;
				}
			}
		}
		if(self.idsList.length){
			self.ids				= '';
			$scope.showBtnValida	= true;
			$scope.showBtnLoad		= false;
			$scope.showTextArea		= false;
		}
	};
	
	self.cancelCarga = function(){
		self.disableMessages();
		$scope.showTextArea		= true;
		$scope.showBtnValida	= false;
		$scope.showBtnLoad		= true;
		$scope.showBtnExecute	= false;
		$scope.showBtnCancel	= false;
		self.idsList			= {};
		self.ids				= '';		
	};
	
	self.disableMessages = function(){
		$scope.vShowMessage		= false;
		$scope.vShowMessageError = false;
	};
	self.disableMessages();
});

scotchApp.controller('cadastroFoneProcon0800Controller', function($scope, $http, $timeout){
	var self 				= this;
	
	self.ufs				= new Array();
	self.city				= new Array();
	
	self.loadScreen = function(){
		$http({
			method: "GET",
			url: "http://localhost:7001/CMS/rest/procon0800Controller/ufs"			
		})
		.then(function sucessCallback(response){
			self.ufs			= response.data;
		}
		, function errorCallback(response){
			alert('error');			
		});
	};
});

scotchApp.controller('FecharContumazAnatel', function($scope, $http, $timeout) {
	var self 				= this;
	self.chamados			= new Array();
	$scope.vShowMessage		= false;
	
	self.callContinueFileUpload=function (){
		self.chamados = new Array();
		var lines 	= $scope.fileContent.split("\n");			
		for (i = 0; i < lines.length; i++) {
			var values	= lines[i].split(";");
			
			self.chamado			= {};
			self.chamado.request	= values[0];
			self.chamado.valor		= values[1];
			self.chamados.push(self.chamado);
		}
	};
	
	self.callExecuteFechamentoMassivo = function(){			
		$http({
			method: "POST",
			url: "http://sv2kprel3:7001/CMS/rest/FecharContumazAnatel/fecharChamados",
			data: self.chamados
		})
		.then(function sucessCallback(response){
			$scope.message = "Chamados fechados com sucesso!";
			$scope.vShowMessage 	= true;
							
			self.clearForm();
		}, function errorCallback(response) {
			self.ocorreuErro(response.data);
		});
	};
	
	self.clearForm = function() {
		self.chamados = [];
	};
	
	self.ocorreuErro = function(msg){
		if(msg == null){
			alert('Ocorreu um erro!');
		}else {
			alert(msg);
		}
	};
	
	self.sucesso = function() {
		alert('Executado com sucesso!');
	};
});

scotchApp.controller('CargaMisController', function($scope, $http) {
	var self 				= this;
	$scope.vShowMessage 	= false;
	
	self.callCargaMis = function(){
		
		var metodo = 'GET';
		$http({
			method: metodo,
			url: "http://sv2kprel3:7001/CMS/rest/cargaMIS/startCarga",
			data: null,				
		}).then(function sucessCallback(response){
			$scope.vShowMessage 	= false;
			$scope.vMessage 		= "Executado com sucesso!";
			self.sucesso();				
		}, function errorCallback(response) {
			$scope.vShowMessage 	= true;
			$scope.vMessage 		= "Ocorreu um erro!";
			self.ocorreuErro();				
		});
		
	};
	
	self.ocorreuErro = function() {
		alert('Ocorreu um erro!');
	};
	
	self.sucesso = function() {
		alert('Executado com sucesso!');
	};
});

scotchApp.controller('gerarTrimestreController', function($scope, $http) {
	var self 				= this;
	$scope.vShowMessage		= false;
	
	self.callContinueFileUpload=function (){
		self.ids = new Array();
		var lines 	= $scope.fileContent.split("\n");			
		for (i = 0; i < lines.length; i++) {
			var values	= lines[i].split(";");
			
			self.item				= {};
			self.item.responsavel	= values[0];
			self.item.id			= values[1];
			self.item.status		= values[2];
			self.item.resposta		= values[3];
			self.ids.push(self.item);
		}
		//controller.ids|json
	};
	
	self.callExecuteGerarTrimestre = function(){
		$http({
			method: "POST",
			url: "http://sv2kprel3:7001/CMS/rest/gerarTrimestre/executar",
			data: self.ids
		})
		.then(function sucessCallback(response){
			$scope.message = "Chamados fechados com sucesso!";
			$scope.vShowMessage 	= true;
			
			$timeout(function(){
			      $scope.doFade = true;
			    }, 2500);
			
			
		}, function errorCallback(response) {
		});
	};
	
});

scotchApp.controller('RoteamentoController', function($scope, $http,$timeout){		
	var self 						= this;
	self.control					= "";		
	$scope.selectedRow 				= null;  // initialize our variable to null
	$scope.selectedRowRoteamento	= null;
	$scope.vShowMessage				= false;
	$scope.vShowError				= false;
	
	$scope.message					= "";
	self.canais						= new Array();
	self.canal						= null;
	self.roteamento					= [];
	self.newCanal					= {};
	
	// OPCOES DO SELECTED DE SITUACOES
	$scope.data = {
		    model: null,
		    availableOptions: [
		      {id: '0', name: 'Ativo'},
		      {id: '1', name: 'Inativo'}
		    ]
		   };
	
	// BUSCA CANAIS NO BANCO
	self.atualizaCanais = function(){
		$http({
			method: "GET",
			url: "http://sv2kprel3:7001/CMS/rest/roteamento/canais"				
		})
		.then(function sucessCallback(response){
			self.canais = response.data;
		}, function errorCallback(response) {
			$scope.message 			= "Erro! " + response.data;
			$scope.vShowMessage 	= true;
		});
	};
	// ATIVAR BOTOES
	self.activeCanais = function(){
		self.atualizaCanais();
		$scope.vShowCanais 				= true;
		self.disableBtns();
		self.control					= "INATIVA";
		$scope.vShowNewRoteamento		= false;
	};
	
	self.activeCanaisAndNewCanal = function(){
		self.atualizaCanais();
		$scope.vShowCanais 				= true;
		$scope.vShowNewCanal 			= true;
		self.disableBtns();
		self.control					= "ATIVA";
	};
	
	self.disableBtns = function(){
		$scope.vBtnCriarRot				= false;
		$scope.vBtnAlterarRot			= false;
		
		$scope.vBtnInicio				= true;
	};
	
	self.defaults = function(){
		$scope.vBtnCriarRot				= true;
		$scope.vBtnAlterarRot			= true;
		$scope.vShowCanais				= false;
		$scope.vShowNewCanal			= false;
		$scope.vShowNewRoteamento		= false;
		$scope.vShowRoteamentos			= false;
		$scope.vShowCadRoteamento		= false;
		$scope.vShowMessage 			= false;
		$scope.vShowError				= false;
		
		$scope.vBtnInicio				= false;
	};
	
	self.canalAtivo = function(){
		self.newCanal.situacao			= "Ativo";
	};
	
	self.roteamentoAtivo = function(){
		self.roteamento.situacao		= "Ativo";
	};
	
	//SELECIONAR CANAL E BUSCAR OS ROTEAMENTOS
	$scope.setClickedRow = function(index){  //function that sets the value of selectedRow to current index
		$scope.selectedRow = index;		
		$http({
			method: "GET",			
			url: "http://sv2kprel3:7001/CMS/rest/roteamento/roteamentos",
			params: self.canais[index]
		})
		.then(function sucessCallback(response){
			self.canais.roteamentos 		= response.data;
			$scope.vShowRoteamentos			= true;
			$scope.vShowCadRoteamento		= false;
			self.canal						= self.canais[$scope.selectedRow];
			if(self.control == "ATIVA"){
				$scope.vShowNewRoteamento		= true;	
			}
		},
		function errorCallback(response) {
			$scope.vShowMessage1 	= true;
			$scope.message 			= "Ocorreu um erro!";
		});
	};
	
	//SELECIONAR ROTEAMENTOS
	$scope.setClickedRowRoteamento = function(index){			
		$scope.selectedRowRoteamento 	= index;
		if(self.control == "INATIVA"){
			$scope.vShowCadRoteamento		= true;
			self.roteamento					= self.canais.roteamentos[index];
		}
	};
	
	// GRAVAR NOVO CANAL
	self.callGravarNewCanal = function(){
		$http({
			method: "POST",
			url: "http://sv2kprel3:7001/CMS/rest/roteamento/newCanal",
			data: self.newCanal
		})
		.then(function sucessCallback(response){
			$scope.vShowMessage1 	= true;
			$scope.message 			= "Gravado com sucesso!";
			
			$timeout(function(){
			      $scope.doFade = true;
			    }, 2500);
			
			self.atualizaCanais();
		}, function errorCallback(response) {
			$scope.vShowMessage1 	= true;
			$scope.message 			= "Ocorreu um erro!";

			$timeout(function(){
			      $scope.doFade = true;
			    }, 2500);
			$("#success-alert").hide();
		});	

	};
	
	// GRAVAR NOVO ROTEAMENTO
	self.callGravarNewRoteamento = function(){			
		if(self.roteamento.situacao == null || self.roteamento.situacao.length == 0){
			alert('Informe a situação!');
		}else{
			$scope.vMessageCadRoteamento			= true;
			self.roteamento							= [];
			$scope.message 							= "Gravado com sucesso!";
		}
		$timeout(function(){
		      $scope.doFade = true;
		    }, 2500);
		
		self.defaults();
		
	};
	
	//GRAVAR CONFIG ROTEAMENTO 
	self.gravar = function(){
		if(self.roteamento.consulta.length == 0){
			$scope.vShowMessage 	= true;
			$scope.message 			= "Informe a consulta de busca aos chamados!";
		}
		else{
			self.roteamento.idCanal = self.canal.idCanal;
			
			$http({
				method: "PUT",
				url: "http://sv2kprel3:7001/CMS/rest/roteamento/updateRoteamento",
				data: self.roteamento
			})
			.then(function sucessCallback(response){

			}, function errorCallback(response){
				$scope.vShowError	 	= true;
				$scope.message 			= "Erro! " + response.data;	
			});
			
			alert("Gravado com sucesso!");
			self.defaults();				
		}
	};
	// REMOVER CANAL
	self.removerCanal = function(index){
		$http({
			method: "PUT",
			url: "http://sv2kprel3:7001/CMS/rest/roteamento/removerCanal",
			data: self.canais[index]
		})
		.then(function sucessCallback(response){
			$scope.vShowMessage 	= true;
			$scope.message 			= "Removido com sucesso!";
			self.atualizaCanais();
		}, function errorCallback(response){
			$scope.vShowError	 	= true;
			$scope.message 			= "Erro! " + response.data;	
		});
	};
	//REMOVER ROTEAMENTO
	self.removerRoteamento = function(){
		self.roteamento.idCanal = self.canal.idCanal;
		$http({
			method: "PUT",
			url: "http://sv2kprel3:7001/CMS/rest/roteamento/removerRoteamento",
			data: self.roteamento
		})
		.then(function sucessCallback(response){
			$scope.vShowMessage 	= true;
			$scope.message 			= "Removido com sucesso!";
		}, function errorCallback(response){
			alert('e');
			$scope.vShowError	 	= true;
			$scope.message 			= "Erro! " + response.data;	
		});
		alert('Roteamento removido com sucesso!');
	};
	
	self.defaults();
});

scotchApp.activate = function (){
	alert('hi');
};