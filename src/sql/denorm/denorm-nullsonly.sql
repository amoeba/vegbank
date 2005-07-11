
update commUsage SET commName = (select commname.commname from commname where commname.commname_ID=commUsage.commName_ID) where commName is null;

update commUsage SET COMMCONCEPT_ID = (select commstatus.commConcept_ID from commStatus where commStatus.commStatus_ID=commUsage.commStatus_ID) where commStatus_ID is not null and commConcept_ID is null;

update plantConcept SET plantName = (select plantname.plantname from plantname where plantname.plantname_ID=plantConcept.plantName_ID) where plantName is null;

update plantUsage SET plantCONCEPT_ID = (select plantstatus.plantConcept_ID from plantStatus where plantStatus.plantStatus_ID=plantUsage.plantStatus_ID) where plantStatus_ID is not null and plantConcept_ID is null;

update plantUsage SET plantName = (select plantname.plantname from plantname where plantname.plantname_ID=plantUsage.plantName_ID) where plantName is null;

update commInterpretation SET commName = (select commname.commname from commname, commconcept where commname.commname_ID=commConcept.commName_ID and commConcept.commConcept_ID=commInterpretation.commconcept_ID) where commName is null;

  UPDATE plot set country = 
    (SELECT 
       (
       SELECT np2.placeName FROM namedPlace as np2 WHERE np2.namedPlace_ID = min(np1.namedPlace_ID)
       )
    FROM namedPlace as np1 , place WHERE  np1.NAMEDPLACE_ID = place.NAMEDPLACE_ID
    and np1.placeSystem='area|country|territory' and place.plot_ID = plot.plot_ID
    )
  WHERE country is null;

update stratum set stratumMethod_ID = (select stratumType.stratumMethod_ID from stratumType where stratumType.stratumType_ID = stratum.stratumType_ID) WHERE stratumMethod_ID is null and stratumType_ID is not null;

update stratum set stratumName = (select stratumType.stratumName from stratumType where stratumType.stratumType_ID = stratum.stratumType_ID) WHERE stratumName is null and stratumType_ID is not null;

update commUsage SET party_ID = (select commstatus.party_ID from commStatus where commStatus.commStatus_ID=commUsage.commStatus_ID) where commStatus_ID is not null and party_ID is null;

update plantUsage SET party_ID = (select plantstatus.party_ID from plantStatus where plantStatus.plantStatus_ID=plantUsage.plantStatus_ID) where plantStatus_ID is not null and party_ID is null;

     UPDATE plot set stateProvince = 
       (SELECT 
          (
          SELECT np2.placeName FROM namedPlace as np2 WHERE np2.namedPlace_ID = min(np1.namedPlace_ID)
          )
       FROM namedPlace as np1 , place WHERE  np1.NAMEDPLACE_ID = place.NAMEDPLACE_ID
       and np1.placeSystem='region|state|province' and place.plot_ID = plot.plot_ID
       )
     WHERE stateProvince is null;
     
     
     

update party set partypublic=true where party_ID in (select party_ID from view_party_public);

update taxonImportance set stratumHeight=(select stratumHeight from stratum where taxonImportance.stratum_ID=stratum.stratum_ID) where taxonImportance.stratum_ID is not null and stratumHeight is null;
update taxonImportance set stratumBase=(select stratumBase from stratum where taxonImportance.stratum_ID=stratum.stratum_ID) where taxonImportance.stratum_ID is not null and stratumBase is null;

update commConcept set commName=(select commName from commName where commName.commName_id=commConcept.commName_ID) where commName is null;

-- update embargo denorm fields for full embargo

-- ORDER OF THESE STATEMENTS MATTERS! the latter read from the former.

--plot
update plot set emb_plot = (select currentEmb from view_emb_embargo_complete where plot.plot_id = view_emb_embargo_complete.plot_ID) where emb_plot is null;

--observation
update observation set emb_observation = (select emb_plot from plot where plot.plot_ID=observation.plot_id) where emb_observation is null;

--disturbanceObs
update disturbanceObs set emb_disturbanceObs = (select emb_observation from observation where observation.observation_ID=disturbanceObs.observation_ID) where emb_disturbanceObs is null;

--soilObs
update soilObs set emb_soilObs = (select emb_observation from observation where observation.observation_ID=soilObs.observation_ID)  where emb_soilObs is null;

--commClass
UPDATE commClass set emb_commClass = (select emb_observation from observation where observation.observation_ID=commClass.observation_ID)  where emb_commClass is null;

---classContributor
UPDATE classContributor SET emb_classContributor= (select emb_commClass from commClass where commClass.commClass_ID=classContributor.commClass_ID) where emb_classContributor is null;

---commInterpretation
UPDATE commInterpretation SET emb_commInterpretation= (select emb_commClass from commClass where commClass.commClass_ID=commInterpretation.commClass_ID) where emb_commInterpretation is null;

--taxonObservation

update taxonObservation set emb_taxonObservation = (select emb_observation from observation where observation.observation_ID=taxonObservation.observation_ID) where emb_taxonObservation is null;

---taxonImportance
update taxonImportance set emb_taxonImportance = (select emb_taxonObservation FROM taxonObservation   where taxonImportance.taxonObservation_id =taxonObservation.taxonObservation_ID) where emb_taxonImportance is null;

----stemCount
update stemCount set emb_stemCount = (select emb_taxonImportance FROM taxonImportance   where stemCount.taxonImportance_id =taxonImportance.taxonImportance_ID) where emb_stemCount is null;

-----stemLocation
update stemLocation set emb_stemLocation = (select emb_stemCount FROM stemCount   where stemLocation.stemCount_id =stemCount.stemCount_ID) where emb_stemLocation is null;

---taxonInterpretation
update taxonInterpretation set emb_taxonInterpretation = (select emb_taxonObservation FROM taxonObservation   where taxonInterpretation.taxonObservation_id =taxonObservation.taxonObservation_ID) where emb_taxonInterpretation is null;

----taxonAlt
update taxonAlt set emb_taxonAlt = (select emb_taxonInterpretation FROM taxonInterpretation   where taxonAlt.taxonInterpretation_id =taxonInterpretation.taxonInterpretation_ID) where emb_taxonAlt is null;

--count of obs
UPDATE plantconcept set d_obsCount = (select count(1) from (select observation_ID from 
  taxoninterpretation, view_notemb_taxonobservation as taxonobservation 
  where taxonobservation.taxonobservation_ID=taxoninterpretation.taxonobservation_id 
  AND plantconcept.plantconcept_id=taxoninterpretation.plantconcept_id
  group by observation_ID ) as foo) where d_obsCount is null;
  
  
UPDATE commconcept set d_obsCount = (select count(1) FROM (select observation_ID from
  comminterpretation, view_notemb_commclass as commclass
  WHERE commclass.commclass_ID=comminterpretation.commclass_id AND commconcept.commconcept_ID=comminterpretation.commconcept_id
  GROUP BY  observation_ID) as foo) where d_obsCount is null;
  
update project set d_obsCount=(select count(1) from view_notemb_observation as observation 
      where observation.project_ID=project.project_ID) where d_obsCount is null;
      
update party set d_obsCount=(select countallcontrib 
   from view_browseparty_all_count where view_browseparty_all_count.party_ID=party.party_ID) where d_obsCount is null;

UPDATE namedplace set d_obsCount=(select count(1) from place where namedplace.namedplacE_ID = place.namedplace_ID)  where d_obsCount is null;

UPDATE taxonobservation set int_origPlantConcept_ID = (select min(plantconcept_ID) from taxoninterpretation 
     WHERE originalinterpretation=true AND taxonobservation.taxonobservation_id = taxoninterpretation.taxonobservation_id) 
   WHERE int_origPlantConcept_ID is null ;

UPDATE taxonobservation set int_origPlantSciName = (select sciname from temptbl_std_plantnames as newnames
     WHERE taxonobservation.int_origPlantConcept_ID = newnames.PlantConcept_ID) 
   WHERE int_origPlantSciName is null ;

UPDATE taxonobservation set int_origPlantSciNameNoAuth = (select scinamenoauth from temptbl_std_plantnames as newnames
     WHERE taxonobservation.int_origPlantConcept_ID = newnames.PlantConcept_ID) 
   WHERE int_origPlantSciNameNoAuth is null ;

UPDATE taxonobservation set int_origPlantCode = (select code from temptbl_std_plantnames as newnames
     WHERE taxonobservation.int_origPlantConcept_ID = newnames.PlantConcept_ID) 
   WHERE int_origPlantCode is null ;

UPDATE taxonobservation set int_origPlantCommon = (select common from temptbl_std_plantnames as newnames
     WHERE taxonobservation.int_origPlantConcept_ID = newnames.PlantConcept_ID) 
   WHERE int_origPlantCommon is null ;

UPDATE taxonobservation set int_currPlantConcept_ID = (select min(plantconcept_ID) from taxoninterpretation 
     WHERE originalinterpretation=true AND taxonobservation.taxonobservation_id = taxoninterpretation.taxonobservation_id) 
   WHERE int_currPlantConcept_ID is null ;

UPDATE taxonobservation set int_currPlantSciName = (select sciname from temptbl_std_plantnames as newnames
     WHERE taxonobservation.int_currPlantConcept_ID = newnames.PlantConcept_ID) 
   WHERE int_currPlantSciName is null ;

UPDATE taxonobservation set int_currPlantSciNameNoAuth = (select scinamenoauth from temptbl_std_plantnames as newnames
     WHERE taxonobservation.int_currPlantConcept_ID = newnames.PlantConcept_ID) 
   WHERE int_currPlantSciNameNoAuth is null ;

UPDATE taxonobservation set int_currPlantCode = (select code from temptbl_std_plantnames as newnames
     WHERE taxonobservation.int_currPlantConcept_ID = newnames.PlantConcept_ID) 
   WHERE int_currPlantCode is null ;

UPDATE taxonobservation set int_currPlantCommon = (select common from temptbl_std_plantnames as newnames
     WHERE taxonobservation.int_currPlantConcept_ID = newnames.PlantConcept_ID) 
   WHERE int_currPlantCommon is null ;
