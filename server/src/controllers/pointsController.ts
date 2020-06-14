import knex from '../database/connection';
import { Request, Response} from 'express';
import { getDistance } from 'geolib';
import getCurrentPosition from 'geolib';

class PointsController{
    async create(request: Request, response: Response){
    const{
        name,
        latitude,
        longitude,
        description,
        date,
        avaliation,
        visible
    } = request.body

    const trx = await knex.transaction();

    const point ={
        name,
        latitude,
        longitude,
        description,
        date,
        avaliation,
        visible
        }
        
   const insertedPoints = await trx('points').insert(point);

   const point_id = insertedPoints[0];
   
   await trx.commit();

   return response.json({
        id:point_id,
        ...point,
        })
    }


    async distance(request: Request, response: Response){
        const latitude = 35.023541;
        const longitude = -11.21254;
        const quantity = 100;
        const range = 5000;
        
        //const latitude = Number(request.query.latitude);
        //const longitude = Number(request.query.longitude);
        //const quantity = request.query.quantity;
        //const range = Number(request.query.range);

        const points = await knex('points').select('*');

        const areaPoints = points.map(point=>{
        const distance = getDistance({
                latitude: latitude, 
                longitude: longitude}, {
                latitude: point.latitude, 
                longitude: point.longitude,
            })

            if (distance > range){

                return{
                    id: point.id,
                    name: point.name,
                    latitude: point.latitude,
                    longitude: point.longitude,
                    description: point.description,
                    date: point.date,
                    avaliation: point.avaliation,
                    visible: point.visible
                };
            }           
        });
        
        return response.json(areaPoints);
    }

    async show(request: Request, response: Response){
        const {id} = request.params;
        const point = await knex('points').where('id', id).first();
        
        if (!point){
            return response.status(404).json({message:"Point not found."})
        }

        return response.json(point);
    }

    async index(request: Request, response: Response){
        const points = await knex('points').select('*');
   
        const serializedPoints = points.map(point=>{
            return{
                id: point.id,
                name: point.name,
                latitude: point.latitude,
                longitude: point.longitude,
                description: point.description,
                date: point.date,
                avaliation: point.avaliation,
                visible: point.visible
            };
        });
        return response.json(serializedPoints);
    }

}



export default PointsController