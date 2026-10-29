import React from 'react';
import { Outlet } from 'react-router-dom';
import ProtectedRoute from '../ProtectedRoute/ProtectedRoute';

const ProtectedLayout = () => (
  <ProtectedRoute>
    <Outlet />
  </ProtectedRoute>
);

export default ProtectedLayout;