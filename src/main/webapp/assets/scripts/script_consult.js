
  $(document)
             .ready(
                           function() {
                                  $('#search_table')
                                               .DataTable(
                                                            {
                                                                   "lengthMenu" : [ [30,50,100,-1 ],
                                                                                 [30,50,100,"Tous"] ],
                                                                   "order" : [ [ 0, "asc" ] ],
                                                                   responsive : true,
                                                                   "language" : {
                                                                          "sProcessing" : "Procesando...",
                                                                          "sLengthMenu" : "Filter _MENU_",
                                                                          "sInfo" : "Affichage de _START_ à _END_ sur _TOTAL_",
                                                                          "sInfoPostFix" : "",
                                                                          "sInfoFiltered" : "",
                                                                          "sSearch" : "Rechercher:",
                                                                          "sUrl" : "",
                                                                          "sInfoThousands" : ",",
                                                                          "oPaginate" : {
                                                                                 "sFirst" : "Premiér",
                                                                                 "sLast" : "Dernier",
                                                                                 "sNext" : "Suivant",
                                                                                 "sPrevious" : "Précédent"
                                                                          },
                                                                          "oAria" : {

                                                                          }
                                                                   }
                                                            });

                           }                      
                           );
