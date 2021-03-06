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

  componentDidMount() {
    this.setState({isLoading: true});
    fetch('api/groups')
      .then(response => response.json())
      .then(data => this.setState({groups: data, isLoading:  false}));
  }

  async remove(uuid) {
    await fetch(`/api/group/${uuid}`, {
      method: 'DELETE', headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
    }).then(() => {
      let updatedGroups = [...this.state.groups].filter(i => i.uuid !== uuid);
      this.setState({groups: updatedGroups});
    });
  }

  render() {
    const {groups, isLoading} = this.state;
    if (isLoading || !groups) {
      return <p>Loading...</p>;
    }
    const groupList = groups.map(group => {
      const address = `${group.address || ''} ${group.city || ''} ${group.stateOrProvince || ''} `;
      return <tr key={group.uuid}>
        <td>{group.name}</td>
        <td>{address}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/groups/" + group.uuid}>Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(group.uuid)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });
    return (
      <div>
        <AppNavbar></AppNavbar>
          <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/groups/new">Add Group</Button>
          </div>
          <h3>Groups</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Name</th>
              <th width="20%">Location</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>{groupList}</tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default GroupList;
