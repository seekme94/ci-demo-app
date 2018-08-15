import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class GroupList extends Component {

  constructor(props) {
    super(props);
    this.state = {groups: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  /* Keep the loading status true until API call is complete */
  componentDidMount() {
    this.setState({isLoading: true});
    fetch('api/groups')
      .then(response => response.json())
      .then(data => this.setState({groups: data, isLoading: false}));
  }

  /* Delete a group by ID and update the groups in state */
  async remove(id) {
      await fetch('/api/group/${id}', {
        method: 'DELETE',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      }).then(() => {
        // Remove the ID from group which has been deleted
        let updatedGroups = [...this.state.groups].filter(i => i.id !== id);
        this.setState({groups: updatedGroups});
      });
    }

    render() {
      const {groups, isLoading} = this.state;
      if (isLoading) {
        return <p>Loading...</p>;
      }
      const groupList = groups.map(group => {
        const address = ${group.address || ''} ${group.city || ''} ${group.stateOrProvince || ''};
        const dateFormat = new Intl.DateTimeFormat('en-US', {year: 'numeric', month: 'long', day: '2-digit'});
        return <tr key={group.id}>
          <td style={{whiteSpace: 'nowrap'}}>{group.name}</td>
          <td>address</td>
          <td>{group.events.map(event => {
            return <div key={event.id}>
            {dateFormat.format(new Date(event.date))}:{event.title}
            </div>
          })}
          </td>
          <td>
            <ButtonGroup>
              <Button size="sm" color="primary" tag={Link} to={"/groups/" + group.id}>Edit</Button>
              <Button size="sm" color="danger" onClick={() => this.remove(group.id)}>Delete</Button>
            </ButtonGroup>
          </td>
        </tr>
      });

      return (
        <div>
          <AppNavbar/>
          <Container fluid>
            <div className="float-right">
              <Button color="success" tag={Link} to="/groups/new">Add Group</Button>
            </div>
            <h3>Group List</h3>
            <Table className="mt-4">
              <thead>
              <tr>
                <th width="20%">Name</th>
                <th width="20%">Location</th>
                <th>Events</th>
                <th width="10%">Actions</th>
              </tr>
              </thead>
              <tbody>
                {groupList}
              </tbody>
            </Table>
          <Container>
        </div>
      );
     }
   }
}

export default GroupList;
