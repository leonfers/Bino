import express from 'express';
import knex from './database/connection';

import PointsController from './controllers/pointsController';

const routes = express.Router();
const pointsControllers = new PointsController();

routes.get('/points', pointsControllers.index);
routes.post('/points', pointsControllers.create);
routes.get('/points/:id', pointsControllers.show);
routes.post('/points/distance', pointsControllers.distance);

export default routes;