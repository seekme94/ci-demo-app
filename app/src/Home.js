import React, { Component } from 'react';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';
import './App.css';

class Home extends Component {
  render() {
    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <Button color="link"><Link to="/groups">Manage Groups</Link></Button>
          <Button color="link"><Link to="/events">Manage Events</Link></Button>
        </Container>
      </div>
    );
  }
}

export default Home;
