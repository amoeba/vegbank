drop view view_plantconcept_transl;
create view view_plantconcept_transl AS SELECT plantconcept.plantconcept_id, plantconcept.plantname_id, plantconcept.plantname, plantconcept.reference_id, ((plantconcept.plantname::text || ' ['::text) || ((( SELECT view_reference_transl.reference_id_transl FROM view_reference_transl WHERE view_reference_transl.reference_id = plantconcept.reference_id))::text)) || ']'::text AS plantconcept_id_transl FROM plantconcept;
